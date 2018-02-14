package ru.lagoshny;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for docker-compose commands
 *
 * Date: 07.02.18
 * Time: 14:32
 *
 * @author ilya@lagoshny.ru
 * @version 1.0
 */
abstract class AbstractDockerComposeCommandMojo extends AbstractMojo {

    /**
     * If true, then for docker-compose command will be add -d flag
     */
    @Parameter(defaultValue = "false")
    protected boolean detachedMode;

    /**
     * Set path to docker compose configuration file
     */
    @Parameter()
    private String dockerComposeFile;

    /**
     * If true, then for docker-compose command will be add --verbose flag
     */
    @Parameter(defaultValue = "false")
    private boolean verbose;

    /**
     * If true, then plugin doesn't start
     */
    @Parameter(defaultValue = "false")
    boolean skip;

    /**
     * The name of the application for which the docker-compose is run
     */
    @Parameter()
    private String applicationName;

    /**
     * Project root folder
     */
    @Parameter()
    private String projectDir;

    /**
     * The name of the container for which the docker-compose is run
     */
    @Parameter()
    protected String containerName;

    void execute(List<String> args) throws MojoExecutionException, MojoFailureException {

        if (StringUtils.isNotEmpty(containerName)) {
            args.add(containerName);
        }

        List<String> cmdCommand =  buildCmdCommand(args);
        ProcessBuilder processBuilder = new ProcessBuilder(cmdCommand).inheritIO();

        try {
            Process process = processBuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                getLog().info(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException(IOUtils.toString(process.getErrorStream(), Charset.forName("UTF-8")));
            }

        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage());
        }

    }


    private List<String> buildCmdCommand(List<String> args) {

        List<String> cmd = new ArrayList<>();
        cmd.add("docker-compose");

        if (StringUtils.isNotEmpty(this.dockerComposeFile)) {
            String composeFilePath = Paths.get(this.dockerComposeFile).toString();
            if (StringUtils.isNotEmpty(composeFilePath)) {
                getLog().info(String.format("Running with custom location docker-compose file: %s", composeFilePath));
                cmd.add("-f");
                cmd.add(composeFilePath);
            }
        }

        if (verbose) {
            getLog().info("Running with --verbose flag");
            cmd.add("--verbose");
        }

        cmd.addAll(args);
        return cmd;
    }

    public boolean isDetachedMode() {
        return detachedMode;
    }

    public String getDockerComposeFile() {
        return dockerComposeFile;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isSkip() {
        return skip;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public String getContainerName() {
        return containerName;
    }
}
