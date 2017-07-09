package org.itai.expenses.core;

import org.apache.commons.lang3.StringUtils;

public class Category {

   private String description;

   public static Category get(String description) {
      return new Category(description);
   }

   public String getDescription() {
      return this.description;
   }

   private Category(String description) {
      this.description = description;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Category)) {
         return false;
      }

      Category other = (Category) obj;
      return StringUtils.equals(this.getDescription(), other.getDescription());
   }

   @Override
   public int hashCode() {
      return this.getDescription().hashCode();
   }
}
