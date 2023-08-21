package cn.vanillazi.learn;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo( name = "echo", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class EchoMojo extends AbstractMojo {

    @Parameter( defaultValue = "${alias}", property = "alias1", required = true )
    private String alias;

    public void execute() throws MojoExecutionException {
        getLog().info("echo:"+alias);
    }
}
