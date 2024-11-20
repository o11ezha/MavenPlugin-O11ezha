package org.plugin;

import org.apache.maven.model.Developer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;

@Mojo(name = "show-developers-info")
public class DeveloperInfoMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Project Developer Info:");

        List<Developer> developersList = project.getDevelopers();

        getLog().info("-----------Project Developers-----------");
        if (developersList.isEmpty()) {
            getLog().info("No developers found in <developers> section.");
            return;
        }

        for (Developer developer : developersList) {
            getLog().info(String.format("Name: %s", safeValue(developer.getName())));
            getLog().info(String.format("Email: %s", safeValue(developer.getEmail())));
            getLog().info(String.format("Organization: %s", safeValue(developer.getOrganization())));
            getLog().info(String.format("Roles: %s", String.join(", ", developer.getRoles() == null ? "None"
                    : String.join(", ", developer.getRoles()))));
            getLog().info("----------------------------------------");
        }
    }

    private String safeValue(String value) {
        return value != null ? value : "Not specified";
    }
}
