package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;
import java.util.HashMap;

public class CustomerMoneyService {

    private HashMap<Customer, BigDecimal> kasa = new HashMap<>();
    private final PersistenceLayer persistenceLayer;

    public CustomerMoneyService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    public BigDecimal getMoney(Customer customer) {
        if (kasa.containsKey(customer)) {
            return kasa.get(customer);
        } else {
            kasa.put(customer, BigDecimal.TEN);
            persistenceLayer.saveCustomer(customer);
            return kasa.get(customer);
        }
    }

    public boolean pay(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        if (money.compareTo(amount) >= 0) {
            kasa.put(customer, money.subtract(amount));
            persistenceLayer.saveCustomer(customer);
            return true;
        }
        return false;
    }

    public void addMoney(Customer customer, BigDecimal amount) {
        BigDecimal money = getMoney(customer);
        persistenceLayer.saveCustomer(customer);
        kasa.put(customer, money.add(amount));
    }

}
