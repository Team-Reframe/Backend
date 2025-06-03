package backend.receipt.love.domain;


import backend.receipt.store.domain.Store;
import jakarta.persistence.*;

import lombok.Getter;




@Entity
@Getter

public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "love_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_Id")
    private Store store;


    private Long memberId;

    public void initialize(Long memberId,Store store) {
        this.memberId = memberId;
        this.store = store;

    }
}
