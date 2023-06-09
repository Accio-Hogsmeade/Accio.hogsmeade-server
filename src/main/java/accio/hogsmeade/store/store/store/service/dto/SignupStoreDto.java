package accio.hogsmeade.store.store.store.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SignupStoreDto {
    private String loginId;
    private String loginPw;
    private String shopkeeper;
    private String tel;
    private String email;
    private String storeName;
    private String storeInfo;

    @Builder
    public SignupStoreDto(String loginId, String loginPw, String shopkeeper, String tel, String email, String storeName, String storeInfo) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.shopkeeper = shopkeeper;
        this.tel = tel;
        this.email = email;
        this.storeName = storeName;
        this.storeInfo = storeInfo;
    }
}
