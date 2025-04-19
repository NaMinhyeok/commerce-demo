package com.nmh.commerce.learning;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
public class CouponStockMappingTest {

    @Autowired
    private EntityManager em;

    @Test
    void 양방향_일대일_관계에서는_mappedBy쪽을_조회하면_Lazy설정을해도_지연로딩이_적용되지_않는다() {
        OneToOneCoupon coupon = new OneToOneCoupon();
        OneToOneCouponStock stock = new OneToOneCouponStock();

        coupon.setStock(stock);
        stock.setCoupon(coupon);

        em.persist(coupon);
        em.persist(stock);
        em.flush();
        em.clear();

        OneToOneCoupon findCoupon = em.find(OneToOneCoupon.class, coupon.getId());

        boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(findCoupon.getStock());
        then(isLoaded).isTrue();

        /*
        *
        Hibernate:
            select
                otoc1_0.id,
                s1_0.id,
                s1_0.remaining_quantity
            from
                one_to_one_coupon otoc1_0
            left join
                one_to_one_coupon_stock s1_0
                on otoc1_0.id=s1_0.coupon_id
            where
                otoc1_0.id=?

            실제로 내가 조회한건 coupon이지만, 즉시 stock까지 조회하는 join 쿼리가 나감
            mappedBy쪽을 조회하면 Lazy설정이 무시됨
        * */
    }

    @Test
    void 양방향_일대일_관계에서는_주인쪽을_조회하면_지연로딩을_적용할_수_있다() {
        OneToOneCoupon coupon = new OneToOneCoupon();
        OneToOneCouponStock stock = new OneToOneCouponStock();

        coupon.setStock(stock);
        stock.setCoupon(coupon);

        em.persist(coupon);
        em.persist(stock);
        em.flush();
        em.clear();

        OneToOneCouponStock findStock = em.find(OneToOneCouponStock.class, stock.getId());
        boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(findStock.getCoupon());
        then(isLoaded).isFalse();
    }

    @Test
    void 단방향_일대일_관계에서는_지연로딩을_사용_할_수_있다() {
        OneToOneCouponHasNotRelation coupon = new OneToOneCouponHasNotRelation();
        OneToOneCouponStockOneWayDirection stock = new OneToOneCouponStockOneWayDirection();
        stock.setCoupon(coupon);

        em.persist(coupon);
        em.persist(stock);
        em.flush();
        em.clear();

        OneToOneCouponStockOneWayDirection findStock = em.find(OneToOneCouponStockOneWayDirection.class, stock.getId());

        boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(findStock.getCoupon());
        then(isLoaded).isFalse();
    }

}
