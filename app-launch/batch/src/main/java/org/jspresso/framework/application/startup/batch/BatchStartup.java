/*
 * Copyright (c) 2005-2013 Vincent Vandenschrick. All rights reserved.
 *
 *  This file is part of the Jspresso framework.
 *
 *  Jspresso is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Jspresso is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Jspresso.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jspresso.framework.application.startup.batch;

import java.util.Locale;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jspresso.framework.application.launch.batch.BatchLauncher;
import org.jspresso.framework.application.startup.BackendActionStartup;

/**
 * A simple batch process starter. The batch itself is coded as a backend action
 * that is executed by the backend controller.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class BatchStartup extends BackendActionStartup implements IBatchStartup {

  private static final String ACTION_ID       = "action";
  private static final String APP_CONTEXT     = "applicationContext";
  private static final String BATCH_USER_NAME = "batchUserName";
  private static final String LOCALE          = "locale";

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean parseCmdLine(String[] args) {
    Options options = createOptions();
    CommandLineParser parser = new BasicParser();
    try {
      CommandLine cmd = parser.parse(options, args);
      processCommandLine(cmd);
    } catch (ParseException ex) {
      System.err.println(ex.getLocalizedMessage());
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(BatchLauncher.class.getSimpleName(), options);
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start() {
    startController();
    if (!executeAction()) {
      System.exit(1);
    }
  }

  /**
   * Creates the CLI options.
   * 
   * @return the CLI options.
   */
  @SuppressWarnings("static-access")
  protected Options createOptions() {
    Options options = new Options();
    options.addOption(OptionBuilder.withArgName(APP_CONTEXT).hasArg()
        .isRequired().withDescription("use given Spring application context.")
        .create(APP_CONTEXT));
    options.addOption(OptionBuilder.withArgName(LOCALE).hasArg()
        .withDescription("use given language (defaults to 'en').")
        .create(LOCALE));
    options.addOption(OptionBuilder.withArgName(ACTION_ID).hasArg()
        .isRequired().withDescription("use the specified backend action.")
        .create(ACTION_ID));
    options.addOption(OptionBuilder.withArgName(BATCH_USER_NAME).hasArg()
        .withDescription("use the specified batch user name.")
        .create(BATCH_USER_NAME));
    return options;
  }

  /**
   * Processes the command line.
   * 
   * @param cmd
   *          the parsed command line.
   * @throws ParseException
   *           whenever an error occurs parsing the command line.
   */
  protected void processCommandLine(CommandLine cmd) throws ParseException {
    setApplicationContextKey(cmd.getOptionValue(APP_CONTEXT));
    setStartupLocale(new Locale(cmd.getOptionValue(LOCALE, "en")));
    setActionBeanId(cmd.getOptionValue(ACTION_ID));
    setUserName(cmd.getOptionValue(BATCH_USER_NAME, "batch"));
  }
}
