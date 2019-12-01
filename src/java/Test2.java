//
//import thanhnd.entity.Category;
//import thanhnd.entity.Place;
//import thanhnd.repository.CategoryRepository;
//import thanhnd.repository.PlaceRepository;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author thanh
// */
//public class Test2 {
//    public static void main(String[] args) {
//        PlaceRepository placeRepository = new PlaceRepository();
//        CategoryRepository categoryRepository = new CategoryRepository();
////        Place place = new Place();
////        place.setName("Quan An Dep Trai");
////        place.setCity("Ha Noi");
////        
////        Category cate1 = new Category();
////        cate1.setName("Pizza");
////        
////        Category cate2 = new Category();
////        cate2.setName("Mon Phap");
////        
////        place.addCategory(cate1);
////        place.addCategory(cate2);
////        
////        placeRepository.save(place);
//        
//        Category category = categoryRepository.getCategoryByName("Pizza").orElse(null);
//        if (category != null){
//            category.getPlaces().stream()
//                    .forEach(c -> System.out.println(c.getName()));
//        }
//    }
//}
