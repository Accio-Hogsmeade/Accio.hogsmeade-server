package accio.hogsmeade.store.client.point.service;

import accio.hogsmeade.store.client.member.Member;
import accio.hogsmeade.store.client.member.repository.MemberRepository;
import accio.hogsmeade.store.client.point.Point;
import accio.hogsmeade.store.client.point.repository.PointRepository;
import accio.hogsmeade.store.common.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static accio.hogsmeade.store.client.member.Identity.STUDENT;
import static accio.hogsmeade.store.client.member.SchoolGroup.GRYFFINDOR;
import static accio.hogsmeade.store.common.Active.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointServiceTest {

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
        Point point = insertPoint(member, 0);

        //when
        Long pointId = pointService.addPoint(member.getLoginId(), 10000);

        //then
        Optional<Point> findPoint = pointRepository.findById(pointId);
        assertThat(findPoint).isPresent();
        assertThat(findPoint.get().getSavedPoint()).isEqualTo(100);
    }

    @Test
    @DisplayName("포인트 사용")
    void usePoint() {
        //given
        Member member = insertMember();
        Point point = insertPoint(member, 10000);

        //when
        Long pointId = pointService.usePoint(member.getLoginId(), 5000);

        //then
        Optional<Point> findPoint = pointRepository.findById(pointId);
        assertThat(findPoint).isPresent();
        assertThat(findPoint.get().getSavedPoint()).isEqualTo(5000);
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

    private Point insertPoint(Member member, int savedPoint) {
        Point point = Point.builder()
                .savedPoint(savedPoint)
                .member(member)
                .histories(new ArrayList<>())
                .build();
        return pointRepository.save(point);
    }
}