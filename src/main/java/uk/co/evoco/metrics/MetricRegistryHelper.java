package uk.co.evoco.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

public class MetricRegistryHelper {

    private static MetricRegistryHelper metricRegistryHelper;
    private static MetricRegistry metricRegistry;
    private static JmxReporter jmxReporter;
//    private static GraphiteReporter graphiteReporter;
//    private static final Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
    /**
     *
     */
    private MetricRegistryHelper() {
        this.metricRegistry = new MetricRegistry();
        jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();

//        graphiteReporter = GraphiteReporter.forRegistry(metricRegistry)
//                .prefixedWith("selenium-pom-framework")
//                .convertRatesTo(TimeUnit.SECONDS)
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .filter(MetricFilter.ALL)
//                .build(graphite);
//        graphiteReporter.start(30, TimeUnit.SECONDS);
    }

    /**
     *
     * @return MetricRegistryHelper returns the instance of the singleton to access the member methods
     */
    public static MetricRegistry get() {
        if(null == metricRegistryHelper) {
            metricRegistryHelper = new MetricRegistryHelper();
        }
        return metricRegistry;
    }
}
