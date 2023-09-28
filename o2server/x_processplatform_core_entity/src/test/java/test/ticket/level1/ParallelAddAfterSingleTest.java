package test.ticket.level1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.x.processplatform.core.entity.ticket.Ticket;
import com.x.processplatform.core.entity.ticket.Tickets;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParallelAddAfterSingleTest {

	@DisplayName("B后加签EFG,FEG任意处理,A先处理再EFG任意处理,再C处理")
	@Test
	@Order(1)
	void test01() {

		List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"), new Ticket("C", "LC"));
		List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"), new Ticket("G", "LG"));
		List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"), new Ticket("K", "LK"));

		Tickets tickets = Tickets.parallel(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LB");
		tickets.add(opt.get(), p2, false, Tickets.MODE_SINGLE);
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,F,G", value);

		tickets.completed("LA");
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("C,E,F,G", value);
		tickets.completed("LF");
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("C", value);
		tickets.completed("LC");

		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}

	@DisplayName("B后加签EFG,FEG任意处理,A,C先处理")
	@Test
	@Order(2)
	void test02() {
		List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"), new Ticket("C", "LC"));
		List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"), new Ticket("G", "LG"));
		List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"), new Ticket("K", "LK"));
		Tickets tickets = Tickets.parallel(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LB");
		tickets.add(opt.get(), p2, false, Tickets.MODE_SINGLE);
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,F,G", value);

		tickets.completed("LA");
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("C,E,F,G", value);
		tickets.completed("LC");
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("E,F,G", value);
		tickets.completed("LF");

		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}

	@DisplayName("B后加签EFG,FEG任意处理,E先处理一个再AC处理.")
	@Test
	@Order(3)
	void test03() {
		List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"), new Ticket("C", "LC"));
		List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"), new Ticket("G", "LG"));
		List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"), new Ticket("K", "LK"));
		Tickets tickets = Tickets.parallel(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LB");
		tickets.add(opt.get(), p2, false, Tickets.MODE_SINGLE);
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,F,G", value);
		tickets.completed("LE");
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A,C", value);
		tickets.completed("LC");
		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("A", value);
		tickets.completed("LA");

		value = tickets.bubble().stream().<String>map(Ticket::target).collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}

}
