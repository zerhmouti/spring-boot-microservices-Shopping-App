package com.zerhmouti.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="t_inventory")
@Getter  @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String skuCode;
    private Integer quantity;
}
