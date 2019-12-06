/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.hibernate.Session;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import thanhnd.constant.FileConstant;
import thanhnd.dto.PlaceCrawlDto;
import thanhnd.entity.Category;
import thanhnd.entity.Place;
import thanhnd.helper.googleapi.Candidate;
import thanhnd.helper.googleapi.GoogleAPIData;
import thanhnd.repository.CategoryRepository;
import thanhnd.repository.PlaceRepository;
import thanhnd.utils.GoogleAPIUtils;
import thanhnd.utils.StaxUtils;
import thanhnd.utils.TrAXUtils;

/**
 *
 * @author thanh
 */
public class CrawlService {

    private final Session session;
    private final CategoryRepository categoryRepository;
    private final PlaceRepository placeRepository;
    private final String TAG = "CrawlService: ";
    private final String realPath;

    public CrawlService(Session hibernateSession, String realPath) {
        this.session = hibernateSession;
        this.categoryRepository = new CategoryRepository(session);
        this.placeRepository = new PlaceRepository(session);
        this.realPath = realPath;
    }

    public void crawlPasgo(boolean fromFile) {
        ByteArrayOutputStream os = null;
        FileOutputStream fo = null;
        List<PlaceCrawlDto> placelDtoList;
        List<PlaceCrawlDto> placelDtoListValidated;
        try {
            if (fromFile) {
                // get xml from file
                FileInputStream fis = new FileInputStream(realPath + FileConstant.PASGO_OUTPUT_XML);

                // parse by stAX
                placelDtoList = parseDataByStAX(fis);
            } else {
                // crawl from web
                os = TrAXUtils.transform(realPath + FileConstant.PASGO_INPUT_XML, realPath + FileConstant.PASGO_MAIN_XSL);

                // save to file to test
                fo = new FileOutputStream(realPath + FileConstant.PASGO_OUTPUT_XML);
                fo.write(os.toByteArray());
                fo.flush();

                // parse by stAX
                placelDtoList = parseDataByStAX(new ByteArrayInputStream(os.toByteArray()));
            }

            // validate 
            placelDtoListValidated = new ArrayList<>();
            for (PlaceCrawlDto placeCrawlDto : placelDtoList) {
                if (isValid(placeCrawlDto)) {
                    placelDtoListValidated.add(placeCrawlDto);
                }
            }

            // save to db
            this.saveToDb(placelDtoListValidated);

        } catch (Exception ex) {
            Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException ex) {
                    Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
                }
            }
        }
    }

    public void saveToDb(List<PlaceCrawlDto> placeCrawlDtos) {

        placeCrawlDtos.stream().forEach(dto -> {
            Optional<Place> optionalPlace = findPlaceByNameAndAddress(dto.getName(), dto.getFullAddress());
            if (!optionalPlace.isPresent()) {
                GoogleAPIData googleAPIData = GoogleAPIUtils.getGoogleAPIData(dto.getFullAddress());
                if (googleAPIData.getStatus().equals("OK")) {
                    Place place = new Place();
                    place.setName(dto.getName());
                    place.setFullAddress(dto.getFullAddress());
                    place.setImage(dto.getImage());

                    Candidate candidatePlace = googleAPIData.getCandidates().get(0);
                    place.setFullAddressFormatted(candidatePlace.getFormatted_address());
                    place.setLatitude(candidatePlace.getGeometry().getLocation().getLat());
                    place.setLongitude(candidatePlace.getGeometry().getLocation().getLng());
                    System.out.println("change success: " + place.getFullAddressFormatted());

                    for (String categoryString : dto.getCategoriesStringSet()) {
                        Category category = convertStringToCategory(categoryString);
                        place.addCategory(category);
                    }
                    System.out.println("save place " + place.getName());
                    placeRepository.save(place);
                } else {
                    System.out.println("Status: " + googleAPIData.getStatus());
                }
            } else {
                Place place = optionalPlace.get();
                if (!place.getImage().equals(dto.getImage())) {
                    place.setImage(dto.getImage());
                    placeRepository.save(place);
                }
            }
        });
    }

    public Category convertStringToCategory(String categoryString) {
        Optional<Category> optionalCategory = categoryRepository.getCategoryByName(categoryString);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            return new Category(categoryString);
        }
    }

    private Optional<Place> findPlaceByNameAndAddress(String name, String fullAddress) {
        return placeRepository.findByNameAndAddress(name, fullAddress);
    }

    private List<PlaceCrawlDto> parseDataByStAX(InputStream is) throws XMLStreamException {
        PlaceCrawlDto placeCrawlDto = new PlaceCrawlDto();
        HashMap<String, PlaceCrawlDto> mapPlaces = new HashMap<>();
        XMLStreamReader reader = StaxUtils.parseFileToStAXCursor(is);
        while (reader.hasNext()) {
            int currentCursor = reader.next();
            if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                if (reader.getLocalName().trim().equals("place")) {

                    // get child node and create object placeCrawlDto
                    String name = StaxUtils.getTextContentStAXCursor("name", reader).orElse("").trim();
                    String categoriesString = StaxUtils.getTextContentStAXCursor("categoriesString", reader).orElse("").trim();
                    String fullAddress = StaxUtils.getTextContentStAXCursor("fullAddress", reader).orElse("").trim();
                    String image = StaxUtils.getTextContentStAXCursor("image", reader).orElse("").trim();
                    placeCrawlDto = new PlaceCrawlDto(name, image, fullAddress);

                    //convert categoriesStringArray to Set and add to placeCrawlDto object 
                    if (categoriesString != null) {
                        String[] categoriesStringArray = categoriesString.split(",|(và)");
                        HashSet<String> categoriesStringSet = new HashSet<>();
                        for (String category : categoriesStringArray) {
                            categoriesStringSet.add(category.trim());
                        }
                        placeCrawlDto.setCategoriesStringSet(categoriesStringSet);
                    }
                }
            }

            if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                if (reader.getLocalName().trim().equals("place")) {
                    // add or update object placeCrawlDto to Map
                    String placeName = placeCrawlDto.getName();
                    if (mapPlaces.containsKey(placeCrawlDto.getName())) {
                        Set<String> currentCategoriesString = mapPlaces.get(placeName).getCategoriesStringSet();
                        Set<String> newCategoriesString = placeCrawlDto.getCategoriesStringSet();
                        currentCategoriesString.addAll(newCategoriesString);
                    } else {
                        mapPlaces.put(placeName, placeCrawlDto);
                        System.out.println("Add place: " + placeCrawlDto.getName());
                    }
                }
            }
        }
        return new ArrayList<>(mapPlaces.values());
    }

    private boolean isValid(PlaceCrawlDto placeCrawlDto) throws JAXBException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(realPath + FileConstant.XSD_FILE));

            JAXBContext jAXBContext = JAXBContext.newInstance(PlaceCrawlDto.class);
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(placeCrawlDto, new DefaultHandler());
            return true;
        } catch (JAXBException ex) {
            Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void crawlData(String inputXml, String mainXsl, String outputXml, boolean isFromFile) {
        ByteArrayOutputStream os = null;
        FileOutputStream fo = null;
        List<PlaceCrawlDto> placelDtoList;
        List<PlaceCrawlDto> placelDtoListValidated;
        try {
            if (isFromFile) {
                // get xml from file
                FileInputStream fis = new FileInputStream(realPath + outputXml);

                // parse by stAX
                placelDtoList = parseDataByStAX(fis);
            } else {
                // crawl from web
                os = TrAXUtils.transform(realPath + inputXml, realPath + mainXsl);

                // save to file to test
                fo = new FileOutputStream(realPath + outputXml);
                fo.write(os.toByteArray());
                fo.flush();

                // parse by stAX
                placelDtoList = parseDataByStAX(new ByteArrayInputStream(os.toByteArray()));
            }

            // validate 
            placelDtoListValidated = new ArrayList<>();
            for (PlaceCrawlDto placeCrawlDto : placelDtoList) {
                if (isValid(placeCrawlDto)) {
                    placelDtoListValidated.add(placeCrawlDto);
                }
            }

            // save to db
            this.saveToDb(placelDtoListValidated);

        } catch (Exception ex) {
            Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException ex) {
                    Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
                }
            }
        }
    }
}
