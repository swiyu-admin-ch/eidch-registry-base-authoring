/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring;

import ch.admin.bit.jeap.monitor.metrics.health.HealthMetricsAutoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(exclude = {
        // Disable health endpoint invocation on each /actuator/prometheus request
        // once https://jira.bit.admin.ch/browse/JEAP-5387 is fixed, it can be removed
        HealthMetricsAutoConfig.class
})
public class Application {

    public static void main(String[] args) {
        var env = SpringApplication.run(Application.class, args).getEnvironment();
        var appName = env.getProperty("spring.application.name");
        var serverPort = env.getProperty("server.port");
        log.info(
                """
                        
                        ----------------------------------------------------------------------------
                        \t'{}' is running!\s
                        \tProfile(s): \t\t\t\t{}
                        \tSwaggerUI:   \t\t\t\thttp://localhost:{}/swagger-ui.html
                        ----------------------------------------------------------------------------""",
                appName,
                env.getActiveProfiles(),
                serverPort
        );
    }
}
