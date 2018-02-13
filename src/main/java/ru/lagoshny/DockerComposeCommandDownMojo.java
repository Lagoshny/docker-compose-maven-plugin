package ru.lagoshny;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import ru.lagoshny.constants.DockerComposeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Docker compose down command
 *
 * Date: 07.02.18
 * Time: 14:39
 *
 * @author ilya@lagoshny.ru
 * @version 1.0
 */
@Mojo(name = "down", threadSafe = true)
public class DockerComposeCommandDownMojo extends AbstractDockerComposeCommandMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (skip) {
            getLog().info(String.format("Skip execution %s command for container: %s", DockerComposeCommand.DOWN, containerName));
            return;
        }

        getLog().info(String.format("Running %s command for %s container.", DockerComposeCommand.DOWN, containerName));

        List<String> args = new ArrayList<>();
        args.add(DockerComposeCommand.DOWN.getValue());

        super.execute(args);
    }

}
