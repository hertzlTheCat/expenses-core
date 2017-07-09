package org.itai.expenses.core.condition;

import org.itai.expenses.core.Category;
import org.itai.expenses.core.Transaction;

public class CategoryCondition implements Condition {

   private Category category;

   public CategoryCondition(Category category) {
      this.category = category;
   }

   @Override
   public boolean isMatch(Transaction transaction) {
      return category.equals(transaction.getCategory())
         || category.contains(transaction.getCategory());
   }

}
