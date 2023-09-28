package test.ticket.level2;

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
class QueueAddBeforeSingleAndBeforeParallelTest {

	private static final List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"),
			new Ticket("C", "LC"));
	private static final List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"),
			new Ticket("G", "LG"));
	private static final List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"),
			new Ticket("K", "LK"));

	@DisplayName("A前加签EFG,EFG单人处理,F前加签IJK,IJK并行处理，即串行-前加签单人-前加签并行")
	@Test
	@Order(1)
	void test01() {
		Tickets tickets = Tickets.queue(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LA");
		tickets.add(opt.get(), p2, true, Tickets.MODE_SINGLE);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("E,F,G", value);
		Optional<Ticket> opt1 = tickets.findTicketWithLabel("LF");
		tickets.add(opt1.get(), p3, true, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("I,J,K", value);
		tickets.completed("LJ");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("I,K", value);
		tickets.completed("LI");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("K", value);
		tickets.completed("LK");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("E,F,G", value);
		tickets.completed("LF");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A", value);
		tickets.completed("LA");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("B", value);
		tickets.completed("LB");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C", value);
		tickets.completed("LC");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}

}
