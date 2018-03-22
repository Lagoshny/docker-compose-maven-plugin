package ru.lagoshny;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import ru.lagoshny.constants.DockerComposeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Docker compose start command
 *
 * Date: 07.02.18
 * Time: 16:08
 *
 * @author ilya@lagoshny.ru
 * @version 1.0
 */
@Mojo(name = "start", threadSafe = true)
public class DockerComposeCommandStartMojo extends AbstractDockerComposeCommandMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (skip) {
            getLog().info(String.format("Skip execution %s command for service: %s", DockerComposeCommand.START, serviceName));
            return;
        }

        getLog().info(String.format("Running %s command for %s service.", DockerComposeCommand.START, serviceName));

        List<String> args = new ArrayList<>();
        args.add(DockerComposeCommand.START.getValue());

        super.execute(args);
    }

}
