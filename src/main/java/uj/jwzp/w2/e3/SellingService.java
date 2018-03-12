package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {

    private final PersistenceLayer persistenceLayer;
    final CustomerMoneyService moneyService;
    final DiscountsConfigsWrapper discountsConfigsWrapper;

    public SellingService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
        this.discountsConfigsWrapper = DiscountsConfigsWrapper.instanceOfDiscountsConfig;
    }

    SellingService(PersistenceLayer persistenceLayer, DiscountsConfigsWrapper mock) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
        this.discountsConfigsWrapper = mock;
    }

    public boolean sell(Item item, int quantity, Customer customer, BigDecimal discount, Boolean isWeekend) {
        BigDecimal money = moneyService.getMoney(customer);
        BigDecimal price = item.getPrice().subtract(discount).multiply(BigDecimal.valueOf(quantity));
        if (isWeekend && price.compareTo(BigDecimal.valueOf(5)) > 0) {
            price = price.subtract(BigDecimal.valueOf(3));
        }
        boolean sold = moneyService.pay(customer, price);
        if (sold) {
            return persistenceLayer.saveTransaction(customer, item, quantity);
        } else {
            return sold;
        }
    }

}
