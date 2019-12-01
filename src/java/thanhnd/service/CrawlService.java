/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.hibernate.Session;
import thanhnd.constant.FileConstant;
import thanhnd.dto.PlaceCrawlDto;
import thanhnd.entity.Category;
import thanhnd.entity.Place;
import thanhnd.repository.CategoryRepository;
import thanhnd.repository.PlaceRepository;
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

    public CrawlService(Session hibernateSession) {
        this.session = hibernateSession;
        this.categoryRepository = new CategoryRepository(session);
        this.placeRepository = new PlaceRepository(session);
    }

    public void crawlPasgo(String realPath) {
        ByteArrayOutputStream os = null;
        FileOutputStream fo = null;
        try {
            // crawl from web
            os = TrAXUtils.transform(realPath + FileConstant.PASGO_INPUT_XML, realPath + FileConstant.PASGO_MAIN_XSL);

            // save to file to test
            fo = new FileOutputStream(realPath + FileConstant.PASGO_OUTPUT_XML);
            fo.write(os.toByteArray());
            fo.flush();

            // parse by stAX
            HashMap<String, PlaceCrawlDto> mapPlaces = parseDataByStAX(new ByteArrayInputStream(os.toByteArray()));

            // save to db
            this.saveToDb(new ArrayList<>(mapPlaces.values()));

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

    public void crawlPasgoFromFile(String realPath) {
        ByteArrayOutputStream os = null;
        FileOutputStream fo = null;
        try {
            // get xml from file
            FileInputStream fis = new FileInputStream(realPath + FileConstant.PASGO_OUTPUT_XML);

            // Stax
            HashMap<String, PlaceCrawlDto> mapPlaces = parseDataByStAX(fis);

            // save to db
            List<PlaceCrawlDto> placeCrawlDtos = new ArrayList<>(mapPlaces.values());
            this.saveToDb(placeCrawlDtos);

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

    private boolean validateByXsd(byte[] xmlByteArray, String xsdFilePath) {
        StreamSource xmlSource = new StreamSource(new ByteArrayInputStream(xmlByteArray));
        StreamSource xsd = new StreamSource(xsdFilePath); // read schema
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); // create schema factory
            Schema schema = sf.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(xmlSource);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CrawlService.class.getName()).log(Level.SEVERE, TAG + ex.getMessage());
        }
        return false;
    }

    public void saveToDb(List<PlaceCrawlDto> placeCrawlDtos) {
        placeCrawlDtos.stream().forEach(dto -> {
            if (!isPlaceExisted(dto.getName())) {
                Place place = new Place();
                place.setName(dto.getName());
                place.setFullAddress(dto.getFullAddress());
                place.setImage(dto.getImage());
                for (String categoryString : dto.getCategoriesStringSet()) {
                    Category category = convertStringToCategory(categoryString);
                    place.addCategory(category);
                }
                System.out.println("save place " + place.getName());
                placeRepository.save(place);
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

    private boolean isPlaceExisted(String name) {
        return placeRepository.isPlaceExistedByName(name);
    }

    private HashMap<String, PlaceCrawlDto> parseDataByStAX(InputStream is) throws XMLStreamException {
        PlaceCrawlDto placeCrawlDto = new PlaceCrawlDto();
        HashMap<String, PlaceCrawlDto> result = new HashMap<>();
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
                        String[] categoriesStringArray = categoriesString.split(",");
                        HashSet<String> categoriesStringSet = new HashSet<>(Arrays.asList(categoriesStringArray));
                        categoriesStringSet.forEach(cate -> cate.trim());
                        placeCrawlDto.setCategoriesStringSet(categoriesStringSet);
                    }
                }
            }

            if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                if (reader.getLocalName().trim().equals("place")) {
                    // add or update object placeCrawlDto to Map
                    String placeName = placeCrawlDto.getName();
                    if (result.containsKey(placeCrawlDto.getName())) {
                        Set<String> currentCategoriesString = result.get(placeName).getCategoriesStringSet();
                        Set<String> newCategoriesString = placeCrawlDto.getCategoriesStringSet();
                        currentCategoriesString.addAll(newCategoriesString);
                    } else {
                        result.put(placeName, placeCrawlDto);
                        System.out.println("Add new place: " + placeName);
                    }
                }
            }
        }
        return result;
    }
}
