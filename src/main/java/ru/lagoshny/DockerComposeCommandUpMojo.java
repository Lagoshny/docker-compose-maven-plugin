package ru.lagoshny;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.lagoshny.constants.DockerComposeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Docker compose up command
 *
 * Date: 07.02.18
 * Time: 14:40
 *
 * @author ilya@lagoshny.ru
 * @version 1.0
 */
@Mojo(name = "up", threadSafe = true)
public class DockerComposeCommandUpMojo extends AbstractDockerComposeCommandMojo {

    /**
     * If true, then for docker-compose command will be add -build flag
     */
    @Parameter(defaultValue = "false")
    private boolean needBuild;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (skip) {
            getLog().info(String.format("Skip execution %s command for service: %s", DockerComposeCommand.UP, serviceName));
            return;
        }

        StringBuilder logBuilder = new StringBuilder(String.format("Running %s command for %s service",
                DockerComposeCommand.UP, serviceName));

        List<String> args = new ArrayList<>();
        args.add(DockerComposeCommand.UP.getValue());

        if (detachedMode) {
            args.add("-d");
            logBuilder.append(", in detached mode");
        }

        if (needBuild) {
            args.add("--build");
            logBuilder.append(", in build mode");
        }

        getLog().info(logBuilder.toString());

        super.execute(args);
    }

    public boolean isNeedBuild() {
        return needBuild;
    }

}
