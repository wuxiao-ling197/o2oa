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
class ParallelAddBeforeParallelAndAfterParallelTest {

	@DisplayName("B前加签EFG,FEG并行处理,F后加签IJK,IJK并行处理,即并行-前加签并行-后加签并行,混合处理EFGIJK都处理完后到B")
	@Test
	@Order(1)
	void test01() {
		List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"), new Ticket("C", "LC"));
		List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"), new Ticket("G", "LG"));
		List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"), new Ticket("K", "LK"));

		Tickets tickets = Tickets.parallel(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LB");
		tickets.add(opt.get(), p2, true, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,F,G", value);
		Optional<Ticket> opt1 = tickets.findTicketWithLabel("LF");
		tickets.add(opt1.get(), p3, false, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,G,I,J,K", value);
		tickets.completed("LA");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C,E,G,I,J,K", value);
		tickets.completed("LE");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C,G,I,J,K", value);
		tickets.completed("LG");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C,I,J,K", value);
		tickets.completed("LI");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C,J,K", value);
		tickets.completed("LC");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("J,K", value);
		tickets.completed("LJ");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("K", value);
		tickets.completed("LK");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("B", value);
		tickets.completed("LB");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}

	@DisplayName("B前加签EFG,FEG并行处理,F后加签IJK,IJK并行处理,即并行-前加签并行-后加签并行,EFGIJK都处理完后到ABC")
	@Test
	@Order(2)
	void test02() {
		List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"), new Ticket("C", "LC"));
		List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"), new Ticket("G", "LG"));
		List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"), new Ticket("K", "LK"));

		Tickets tickets = Tickets.parallel(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LB");
		tickets.add(opt.get(), p2, true, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,F,G", value);
		Optional<Ticket> opt1 = tickets.findTicketWithLabel("LF");
		tickets.add(opt1.get(), p3, false, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,G,I,J,K", value);

		tickets.completed("LJ");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,G,I,K", value);
		tickets.completed("LE");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,G,I,K", value);
		tickets.completed("LG");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,I,K", value);
		tickets.completed("LI");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,K", value);
		tickets.completed("LK");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		tickets.completed("LA");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("B,C", value);
		tickets.completed("LB");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C", value);
		tickets.completed("LC");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}

	@DisplayName("B前加签EFG,FEG并行处理,F后加签IJK,IJK并行处理,即并行-前加签并行-后加签并行,AC先处理,EFGIJK都处理完后到B")
	@Test
	@Order(3)
	void test03() {
		List<Ticket> p1 = Arrays.asList(new Ticket("A", "LA"), new Ticket("B", "LB"), new Ticket("C", "LC"));
		List<Ticket> p2 = Arrays.asList(new Ticket("E", "LE"), new Ticket("F", "LF"), new Ticket("G", "LG"));
		List<Ticket> p3 = Arrays.asList(new Ticket("I", "LI"), new Ticket("J", "LJ"), new Ticket("K", "LK"));

		Tickets tickets = Tickets.parallel(p1);
		String value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,B,C", value);
		Optional<Ticket> opt = tickets.findTicketWithLabel("LB");
		tickets.add(opt.get(), p2, true, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,F,G", value);
		Optional<Ticket> opt1 = tickets.findTicketWithLabel("LF");
		tickets.add(opt1.get(), p3, false, Tickets.MODE_PARALLEL);
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("A,C,E,G,I,J,K", value);

		tickets.completed("LA");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("C,E,G,I,J,K", value);
		tickets.completed("LC");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("E,G,I,J,K", value);
		tickets.completed("LG");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("E,I,J,K", value);
		tickets.completed("LI");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("E,J,K", value);
		tickets.completed("LK");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("E,J", value);
		tickets.completed("LE");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("J", value);
		tickets.completed("LJ");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("B", value);
		tickets.completed("LB");
		value = tickets.bubble().stream().<String>map(Ticket::target).sorted().collect(Collectors.joining(","));
		Assertions.assertEquals("", value);
	}


}
