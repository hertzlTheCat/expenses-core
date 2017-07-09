package org.itai.expenses.core;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

public class Category {

   private String description;
   Collection<Category> subCategories;

   public static Category get(String description) {
      return new Category(description);
   }

   public String getDescription() {
      return this.description;
   }

   public boolean contains(Category category) {
      if (this.subCategories.contains(category)) {
         return true;
      }
      for (Category c : this.subCategories) {
         if (c.contains(category)) {
            return true;
         }
      }
      return false;
   }

   private Category(String description) {
      this.subCategories = new LinkedList<>();
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

   public Category addSubCategory(String description) {
      Category subCategory = Category.get(description);
      this.subCategories.add(subCategory);
      return subCategory;
   }
}
