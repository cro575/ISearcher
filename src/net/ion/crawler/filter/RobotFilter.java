package net.ion.crawler.filter;

import java.io.IOException;
import java.net.URL;

import net.ion.crawler.link.Link;
import net.ion.crawler.util.Robot;

public class RobotFilter implements ILinkFilter {

	private Robot robot;

	public RobotFilter(StringBuffer buffer) {
		this.robot = new Robot(buffer);
	}

	public RobotFilter(URL url) throws IOException {
		this.robot = new Robot(url);
	}

	public boolean accept(Link link) {
		return robot.isAllowedToVisit(link.getURI());
	}

}
