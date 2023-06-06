package accio.hogsmeade.store.client.member.repository;

import accio.hogsmeade.store.client.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
