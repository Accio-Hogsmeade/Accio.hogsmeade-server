package accio.hogsmeade.store.store.store.repository;

import accio.hogsmeade.store.store.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s.id from Store s where s.loginId=:loginId")
    Optional<Long> existLoginId(@Param("loginId") String loginId);

    @Query("select s.id from Store s where s.tel=:tel")
    Optional<Long> existTel(@Param("tel") String tel);

    @Query("select s.id from Store s where s.email=:email")
    Optional<Long> existEmail(@Param("email") String email);

    Optional<Store> findByLoginId(@Param("loginId") String loginId);

    Optional<Store> findByTel(@Param("tel") String tel);

    Optional<Store> findByEmail(@Param("email") String email);
}
