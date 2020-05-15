package ca.jrvs.apps.trading.repo;

import static ca.jrvs.apps.trading.TestUtil.NOT_ID;
import static ca.jrvs.apps.trading.TestUtil.getTraderDavid;
import static ca.jrvs.apps.trading.TestUtil.getTraderYasuo;

import ca.jrvs.apps.trading.model.domain.Trader;

public class TraderRepositoryTest extends BaseRepositoryTest<TraderRepository, Trader, Integer> {

  @Override
  Trader getTestEntity1() {
    return getTraderDavid();
  }

  @Override
  Trader getTestEntity2() {
    return getTraderYasuo();
  }

  @Override
  Integer getNonExistId() {
    return NOT_ID;
  }

  @Override
  Object modifyOneField(Trader trader) {
    final String newName = "Newman";
    trader.setFirstName(newName);
    return newName;
  }

  @Override
  Object getOneField(Trader trader) {
    return trader.getFirstName();
  }
}