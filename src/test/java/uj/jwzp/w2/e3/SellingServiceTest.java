package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;


public class SellingServiceTest {

    @Mock
    private PersistenceLayer persistenceLayer;

    @Mock
    private DiscountsConfigsWrapper discountsConfigsWrapper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @Test
    public void notSell() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigsWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt()))
                .thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.anyString()))
                .thenReturn(new Item("i", new BigDecimal(3)));

        Customer c = persistenceLayer.getCustomerById(1);
        Item i = persistenceLayer.getItemByName("mleko");

        //when
        boolean sold = uut.sell(i, 7, c, DiscountsConfig.getDiscountForItem(i,c), Boolean.FALSE);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.moneyService.getMoney(c));
    }

    @Test
    public void sell() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigsWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt()))
                .thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.anyString()))
                .thenReturn(new Item("i", new BigDecimal(3)));

        Customer c = persistenceLayer.getCustomerById(1);
        Item i = persistenceLayer.getItemByName("mleko");

        //when
        boolean sold = uut.sell(i, 1, c, DiscountsConfig.getDiscountForItem(i,c), Boolean.FALSE);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }


    @Test
    public void getInfoAboutCustomer(){
        //given
        Customer uut = new Customer(32,"Andrzej", "Chrzanow, xyz");

        ///then
        Assert.assertEquals(32, uut.getId());
        Assert.assertEquals("Andrzej", uut.getName());
        Assert.assertEquals("Chrzanow, xyz", uut.getAddress());
    }


    @Test
    public void sellALot() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigsWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt()))
                .thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.anyString()))
                .thenReturn(new Item("i", new BigDecimal(3)));
        Mockito.when(uut.discountsConfigsWrapper.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsConfigsWrapper.getDiscountForItem(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);

        Customer c = persistenceLayer.getCustomerById(1);
        Item i = persistenceLayer.getItemByName("i");
        BigDecimal discount = uut.discountsConfigsWrapper.getDiscountForItem(i,c);
        Boolean isWeekend = uut.discountsConfigsWrapper.isWeekendPromotion();
        uut.moneyService.addMoney(c, new BigDecimal(990));

        //when
        boolean sold = uut.sell(i, 10, c, discount, isWeekend);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(973), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellLittleDuringWeekend() {
        //given
        SellingService uut = new SellingService(persistenceLayer, discountsConfigsWrapper);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt()))
                .thenReturn(new Customer(1, "DasCustomer", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.anyString()))
                .thenReturn(new Item("i", new BigDecimal(3)));
        Mockito.when(uut.discountsConfigsWrapper.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsConfigsWrapper.getDiscountForItem(Mockito.any(), Mockito.any())).thenReturn(BigDecimal.ZERO);

        Customer c = persistenceLayer.getCustomerById(1);
        Item i = persistenceLayer.getItemByName("i");
        BigDecimal discount = uut.discountsConfigsWrapper.getDiscountForItem(i,c);
        Boolean isWeekend = uut.discountsConfigsWrapper.isWeekendPromotion();

        //when
        boolean sold = uut.sell(i, 1, c, discount, isWeekend);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }
    @Test
    public void sellToArkadiusz(){
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.getCustomerById(Mockito.anyInt()))
                .thenReturn(new Customer(1, "Arkadiusz", "Kraków, Łojasiewicza"));
        Mockito.when(persistenceLayer.getItemByName(Mockito.anyString()))
                .thenReturn(new Item("i", new BigDecimal(3)));
        Item i = persistenceLayer.getItemByName("i");
        Customer c = persistenceLayer.getCustomerById(1);

        //when
        boolean sold = uut.sell(i,1, c, DiscountsConfig.getDiscountForItem(i,c), Boolean.FALSE);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(8.5), uut.moneyService.getMoney(c));

    }
}
