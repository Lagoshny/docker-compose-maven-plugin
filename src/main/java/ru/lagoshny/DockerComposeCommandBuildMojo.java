package ru.lagoshny;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.lagoshny.constants.DockerComposeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Docker compose build command
 *
 * Date: 2/12/18
 * Time: 9:48 AM
 *
 * @author ilya@lagoshny.ru
 * @version 1.0
 */
@Mojo(name = "build", threadSafe = true)
public class DockerComposeCommandBuildMojo extends AbstractDockerComposeCommandMojo {

    /**
     * If true, then for docker-compose command will be add --force-rm flag
     */
    @Parameter()
    private boolean forceRm;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (skip) {
            getLog().info(String.format("Skip execution %s command for service: %s", DockerComposeCommand.BUILD, serviceName));
            return;
        }

        StringBuilder logBuilder = new StringBuilder(String.format("Running %s command for %s service.",
                DockerComposeCommand.BUILD, serviceName));

        List<String> args = new ArrayList<>();
        args.add(DockerComposeCommand.BUILD.getValue());

        if (forceRm) {
            args.add("--force-rm");
            logBuilder.append("With --force-rm flag");
        }

        getLog().info(logBuilder.toString());

        super.execute(args);
    }

    public boolean isForceRm() {
        return forceRm;
    }

}
