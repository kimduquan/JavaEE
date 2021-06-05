/**
 * 
 */
package epf.client.management;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

/**
 * @author PC
 *
 */
@Liveness
@Readiness
@ApplicationScoped
public class ManagementHealthCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		final ClassLoadingMXBean classLoading = ManagementFactory.getClassLoadingMXBean();
		final CompilationMXBean compilation = ManagementFactory.getCompilationMXBean();
		final MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
		final MemoryUsage heap = memory.getHeapMemoryUsage();
		final MemoryUsage nonHeap = memory.getNonHeapMemoryUsage();
		final OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
		final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		final ThreadMXBean thread = ManagementFactory.getThreadMXBean();
		return HealthCheckResponse
				.named(getClass().getSimpleName())
				.withData("ClassLoading.totalLoadedClassCount", classLoading.getTotalLoadedClassCount())
				.withData("ClassLoading.unloadedClassCount", classLoading.getUnloadedClassCount())
				.withData("ClassLoading.verbose", classLoading.isVerbose())
				.withData("Compilation.name", compilation.getName())
				.withData("Compilation.totalCompilationTime", compilation.isCompilationTimeMonitoringSupported()?compilation.getTotalCompilationTime():-1)
				.withData("Compilation.compilationTimeMonitoringSupported", compilation.isCompilationTimeMonitoringSupported())
				.withData("Memory.verbose", memory.isVerbose())
				.withData("Memory.objectPendingFinalizationCount", memory.getObjectPendingFinalizationCount())
				.withData("Memory.heapMemoryUsage.committed", heap.getCommitted())
				.withData("Memory.heapMemoryUsage.init", heap.getInit())
				.withData("Memory.heapMemoryUsage.max", heap.getMax())
				.withData("Memory.heapMemoryUsage.used", heap.getUsed())
				.withData("Memory.nonHeapMemoryUsage.committed", nonHeap.getCommitted())
				.withData("Memory.nonHeapMemoryUsage.init", nonHeap.getInit())
				.withData("Memory.nonHeapMemoryUsage.max", nonHeap.getMax())
				.withData("Memory.nonHeapMemoryUsage.used", nonHeap.getUsed())
				.withData("OperatingSystem.arch", operatingSystem.getArch())
				.withData("OperatingSystem.name", operatingSystem.getName())
				.withData("OperatingSystem.version", operatingSystem.getVersion())
				.withData("OperatingSystem.availableProcessors", operatingSystem.getAvailableProcessors())
				.withData("OperatingSystem.systemLoadAverage", String.valueOf(operatingSystem.getSystemLoadAverage()))
				.withData("Runtime.bootClassPath", runtime.isBootClassPathSupported()?runtime.getBootClassPath():"")
				.withData("Runtime.classPath", runtime.getClassPath())
				.withData("Runtime.libraryPath", runtime.getLibraryPath())
				.withData("Runtime.managementSpecVersion", runtime.getManagementSpecVersion())
				.withData("Runtime.name", runtime.getName())
				.withData("Runtime.pid", runtime.getPid())
				.withData("Runtime.specName", runtime.getSpecName())
				.withData("Runtime.specVendor", runtime.getSpecVendor())
				.withData("Runtime.specVersion", runtime.getSpecVersion())
				.withData("Runtime.startTime", runtime.getStartTime())
				.withData("Runtime.uptime", runtime.getUptime())
				.withData("Runtime.vmName", runtime.getVmName())
				.withData("Runtime.vmVendor", runtime.getVmVendor())
				.withData("Runtime.vmVersion", runtime.getVmVersion())
				.withData("Runtime.bootClassPathSupported", runtime.isBootClassPathSupported())
				.withData("Runtime.inputArguments", String.join(" ", runtime.getInputArguments()))
				.withData("Runtime.systemProperties", runtime.getSystemProperties().toString())
				.withData("Thread.currentThreadCpuTime", thread.isCurrentThreadCpuTimeSupported()?thread.getCurrentThreadCpuTime():-1)
				.withData("Thread.currentThreadUserTime", thread.getCurrentThreadUserTime())
				.withData("Thread.totalStartedThreadCount", thread.getTotalStartedThreadCount())
				.withData("Thread.currentThreadCpuTimeSupported", thread.isCurrentThreadCpuTimeSupported())
				.withData("Thread.objectMonitorUsageSupported", thread.isObjectMonitorUsageSupported())
				.withData("Thread.synchronizerUsageSupported", thread.isSynchronizerUsageSupported())
				.withData("Thread.threadContentionMonitoringEnabled", thread.isThreadContentionMonitoringEnabled())
				.withData("Thread.threadContentionMonitoringSupported", thread.isThreadContentionMonitoringSupported())
				.withData("Thread.threadCpuTimeEnabled", thread.isThreadCpuTimeEnabled())
				.withData("Thread.threadCpuTimeSupported", thread.isThreadCpuTimeSupported())
				.withData("Thread.daemonThreadCount", thread.getDaemonThreadCount())
				.withData("Thread.peakThreadCount", thread.getPeakThreadCount())
				.withData("Thread.threadCount", thread.getThreadCount())
				.withData("Thread.deadlockedThreads", Stream
						.of(thread.findDeadlockedThreads())
						.map(String::valueOf)
						.collect(Collectors.joining(","))
						)
				.withData("Thread.monitorDeadlockedThreads", Stream
						.of(thread.findMonitorDeadlockedThreads())
						.map(String::valueOf)
						.collect(Collectors.joining(","))
						)
				.withData("Thread.allThreadIds", Stream
						.of(thread.getAllThreadIds())
						.map(String::valueOf)
						.collect(Collectors.joining(","))
						)
				.up()
				.build();
	}

}
