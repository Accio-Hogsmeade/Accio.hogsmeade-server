package accio.hogsmeade.store.client.member.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class EditAddressDto {

    private String zipcode;
    private String mainAddress;
    private String detailAddress;

    @Builder
    public EditAddressDto(String zipcode, String mainAddress, String detailAddress) {
        this.zipcode = zipcode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
    }
}
