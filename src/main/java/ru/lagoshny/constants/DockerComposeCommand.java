package ru.lagoshny.constants;

/**
 * Enum with docker-compose commands
 *
 * Date: 2/13/18
 * Time: 10:29 AM
 *
 * @author ilya@lagoshny.ru
 * @version 1.0
 */
public enum DockerComposeCommand {

    START("start"),
    STOP("stop"),
    UP("up"),
    DOWN("down"),
    BUILD("build");

    private String value;

    DockerComposeCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
