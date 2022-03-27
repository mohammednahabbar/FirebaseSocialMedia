package com.biosteam99.loginfirabase;

public class PostModel {
   public String title;
   public String desc;
   public String image;
   public String uid;

   public PostModel() {
   }

   public PostModel(String title, String desc, String image, String uid) {
      this.title = title;
      this.desc = desc;
      this.image = image;
      this.uid = uid;
   }
}
