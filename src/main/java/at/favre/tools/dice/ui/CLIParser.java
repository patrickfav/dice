package at.favre.tools.dice.ui;

import at.favre.tools.dice.util.CmdUtil;
import org.apache.commons.cli.*;

public class CLIParser {

    public static final String ARG_FORMAT = "f";
    static final String ARG_LENGTH = "l";
    static final String ARG_COUNT = "c";
    static final String ARG_DEBUG = "d";

    public static Arg parse(String[] args) {
        Options options = setupOptions();
        CommandLineParser parser = new DefaultParser();
        Arg argument = new Arg();

        try {
            CommandLine commandLine = parser.parse(options, args);
            System.out.println(commandLine.getArgList());
            if (commandLine.hasOption("h") || commandLine.hasOption("help")) {
                printHelp(options);
                return null;
            }

            if (commandLine.hasOption("v") || commandLine.hasOption("version")) {
                System.out.println("Version: " + CmdUtil.jarVersion());
                return null;
            }

            if (commandLine.hasOption(ARG_LENGTH)) {
                argument.length = Integer.valueOf(commandLine.getOptionValue(ARG_LENGTH));
            }
            if (commandLine.hasOption(ARG_COUNT)) {
                argument.count = Integer.valueOf(commandLine.getOptionValue(ARG_COUNT));
            }
            if (commandLine.hasOption(ARG_FORMAT)) {
                argument.encoding = commandLine.getOptionValue(ARG_FORMAT);
            }
            argument.debug = commandLine.hasOption(ARG_DEBUG);

        } catch (Exception e) {
            System.err.println(e.getMessage());

            CLIParser.printHelp(options);

            argument = null;
        }

        return argument;
    }

    private static Options setupOptions() {
        Options options = new Options();

        Option count = Option.builder(ARG_COUNT).longOpt("count").argName("number").desc("How many randoms should be generated").hasArgs().build();
        Option format = Option.builder(ARG_FORMAT).longOpt("format").argName("string").hasArgs().desc("What output encode should be used").build();
        Option length = Option.builder(ARG_LENGTH).longOpt("length").argName("number").hasArg().optionalArg(true).desc("Length of the random").build();
        Option debugOpt = Option.builder().longOpt("debug").hasArg(false).desc("Prints additional info for debugging.").build();

        Option help = Option.builder("h").longOpt("help").desc("Prints docs").build();
        Option version = Option.builder("v").longOpt("version").desc("Prints current version.").build();

        OptionGroup mainArgs = new OptionGroup();
        mainArgs.addOption(length);

        options.addOptionGroup(mainArgs);
        options.addOption(count).addOption(format).addOption(debugOpt).addOption(help).addOption(version);

        return options;
    }

    private static void printHelp(Options options) {
        HelpFormatter help = new HelpFormatter();
        help.setWidth(120);
        help.setLeftPadding(4);
        help.setDescPadding(3);
        help.printHelp("-" + ARG_LENGTH + " <length> | --help", "Version:" + CmdUtil.jarVersion(), options, " ", false);
    }
}
