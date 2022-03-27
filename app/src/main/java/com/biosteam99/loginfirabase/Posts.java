package com.biosteam99.loginfirabase;

public class Posts {
   public String title;
   public String image;
   public String desc;

   // Default constructor required for calls to DataSnapshot.getValue(User.class)
   public Posts() {
   }


   public Posts(String title, String image, String desc) {
      this.title = title;
      this.image = image;
      this.desc = desc;
   }


}
