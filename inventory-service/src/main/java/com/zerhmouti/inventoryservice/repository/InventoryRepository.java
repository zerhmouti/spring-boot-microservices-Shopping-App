package com.zerhmouti.inventoryservice.repository;

import com.zerhmouti.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    public Optional<Inventory> findBySkuCode(String skuCode);

    public List<Inventory> findBySkuCodeIn(String[] skuCodes);

}
