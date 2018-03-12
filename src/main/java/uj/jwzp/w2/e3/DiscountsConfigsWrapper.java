package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;
import java.math.BigDecimal;

public class DiscountsConfigsWrapper {
    static final DiscountsConfigsWrapper instanceOfDiscountsConfig = new DiscountsConfigsWrapper();

    public BigDecimal getDiscountForItem(Item item, Customer customer) {
        return DiscountsConfig.getDiscountForItem(item, customer);
    }

    public Boolean isWeekendPromotion() {
        return DiscountsConfig.isWeekendPromotion();
    }
}
