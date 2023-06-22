package accio.hogsmeade.store.client.point.service.impl;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.client.point.Point;
import accio.hogsmeade.store.client.point.repository.PointRepository;
import accio.hogsmeade.store.client.point.service.PointService;
import accio.hogsmeade.store.common.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static accio.hogsmeade.store.client.member.Identity.STUDENT;
import static accio.hogsmeade.store.client.member.SchoolGroup.GRYFFINDOR;
import static accio.hogsmeade.store.common.Active.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointServiceImplTest {

    @Autowired
    private PointService pointService;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("포인트 적립")
    void addPoint() {
        //given
        Member member = insertMember();
        Point point = insertPoint(member);

        //when
        Long pointId = pointService.addPoint(member.getLoginId(), 10000);

        //then
        Optional<Point> findPoint = pointRepository.findById(pointId);
        assertThat(findPoint).isPresent();
        assertThat(findPoint.get().getSavedPoint()).isEqualTo(100);
    }

    private Member insertMember() {
        Member member = Member.builder()
                .loginId("harry")
                .loginPw("harry1234!")
                .name("harry potter")
                .tel("010-1234-1234")
                .email("harry@naver.com")
                .address(Address.builder()
                        .zipcode("12345")
                        .mainAddress("main address")
                        .detailAddress("detail address")
                        .build())
                .identity(STUDENT)
                .schoolGroup(GRYFFINDOR)
                .active(ACTIVE)
                .roles(Collections.singletonList("MEMBER"))
                .build();
        return memberRepository.save(member);
    }

    private Point insertPoint(Member member) {
        Point point = Point.builder()
                .savedPoint(0)
                .member(member)
                .build();
        return pointRepository.save(point);
    }
}