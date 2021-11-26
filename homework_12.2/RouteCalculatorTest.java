import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List<Station> routeTest1 = new ArrayList<>();
    List<Station> expected1 = new ArrayList<>();
    List<Station> routeTest2 = new ArrayList<>();
    List<Station> expected2 = new ArrayList<>();

    @Override
    protected void setUp() throws Exception {

        StationIndex ekb = new StationIndex();

        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        Line line3 = new Line(3, "Третья");

        ekb.addLine(line1);
        ekb.addLine(line2);
        ekb.addLine(line3);

        Station l1_1 = new Station("Пивоварная", line1);
        Station l1_2 = new Station("Дрочильная", line1);
        Station l1_3 = new Station("Водочная", line1);

        Station l2_1 = new Station("Родильная", line2);
        Station l2_2 = new Station("Операционная", line2);
        Station l2_3 = new Station("Хирургическая", line2);

        Station l3_1 = new Station("Футбольная", line3);
        Station l3_2 = new Station("Хоккейная", line3);
        Station l3_3 = new Station("Баскетбольная", line3);

        line1.addStation(l1_1);
        line1.addStation(l1_2);
        line1.addStation(l1_3);
        line2.addStation(l2_1);
        line2.addStation(l2_2);
        line2.addStation(l2_3);
        line3.addStation(l3_1);
        line3.addStation(l3_2);
        line3.addStation(l3_3);

        ekb.addStation(l1_1);
        ekb.addStation(l1_2);
        ekb.addStation(l1_3);
        ekb.addStation(l2_1);
        ekb.addStation(l2_2);
        ekb.addStation(l2_3);
        ekb.addStation(l3_1);
        ekb.addStation(l3_2);
        ekb.addStation(l3_3);

        List<Station> intersection_l1_l2 = new ArrayList<>();
        intersection_l1_l2.add(l1_2);
        intersection_l1_l2.add(l2_1);

        List<Station> intersection_l2_l3 = new ArrayList<>();
        intersection_l2_l3.add(l3_2);
        intersection_l2_l3.add(l2_3);

        ekb.addConnection(intersection_l1_l2);
        ekb.addConnection(intersection_l2_l3);

        RouteCalculator routeCalculator = new RouteCalculator(ekb);
        routeTest1 = routeCalculator.getShortestRoute(l1_1, l3_1);
        routeTest2 = routeCalculator.getShortestRoute(l1_1, l2_2);

        expected1.add(l1_1);
        expected1.add(l1_2);
        expected1.add(l2_1);
        expected1.add(l2_2);
        expected1.add(l2_3);
        expected1.add(l3_2);
        expected1.add(l3_1);

        expected2.add(l1_1);
        expected2.add(l1_2);
        expected2.add(l2_1);
        expected2.add(l2_2);
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(routeTest1);
        double expected = 17;
        assertEquals(expected, actual);
    }

     public void testGetShortestRoute() {
        assertEquals(expected1, routeTest1);
    }

    public void testGetRouteOnTheLine() {
        assertEquals(expected2, routeTest2);
    }
}

