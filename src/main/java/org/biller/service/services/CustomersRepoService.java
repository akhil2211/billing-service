package org.biller.service.services;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.biller.service.entities.CustomerEntity;
import org.biller.service.enums.Attribute;
import org.biller.service.interfaces.FindByAttributeIdentifier;
import org.biller.service.interfaces.IAttributeFinder;
import org.biller.service.models.AttributeIdentifiers;
import org.biller.service.repos.CustomersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersRepoService implements FindByAttributeIdentifier<CustomerEntity> {

  @Autowired
  private CustomersRepo repo;

  private EnumMap<Attribute, IAttributeFinder<CustomerEntity>> map = new EnumMap<>(Attribute.class);

  @PostConstruct
  private void init() {
    map.put(Attribute.MOBILE_NUMBER, x -> repo.findByPhoneAndActiveTrue(x[0]));
    map.put(Attribute.EMAIL_ID, x -> repo.findByEmailAndActiveTrue(x[0]));
    map.put(Attribute.ACCOUNT_ID, x -> repo.findByAccountIdAndActiveTrue(x[0]));
    map.put(Attribute.UNKOWN, x -> Optional.empty());
  }

  @Override
  public Optional<CustomerEntity> findByAtrributeIdentifier(AttributeIdentifiers identifiers) {
    return map.get(identifiers.getAttributeName()).find(identifiers.getAttributeValue());
  }

  @Override
  public Optional<CustomerEntity> findByMultipleAtrributeIdentifier(
      List<AttributeIdentifiers> identifiers) {
    // TODO Auto-generated method stub
    return null;
  }

}
