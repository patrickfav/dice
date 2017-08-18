package at.favre.tools.dice.ui;

import at.favre.tools.dice.util.CmdUtil;
import org.apache.commons.cli.*;

import java.util.List;

public class CLIParser {
    static final String ARG_ENCODING = "e";
    static final String ARG_COUNT = "c";
    static final String ARG_SEED = "s";
    static final String ARG_ONLINE = "o";
    static final String ARG_DEBUG = "d";

    public static Arg parse(String[] args) {
        Options options = setupOptions();
        CommandLineParser parser = new DefaultParser();
        Arg argument = new Arg();

        try {

            CommandLine commandLine = parser.parse(options, args);
            argument.length = parseLength(commandLine.getArgList());

            if (commandLine.hasOption("h") || commandLine.hasOption("help")) {
                printHelp(options);
                return null;
            }

            if (commandLine.hasOption("v") || commandLine.hasOption("version")) {
                System.out.println("Version: " + CmdUtil.jarVersion());
                return null;
            }

            if (commandLine.hasOption(ARG_COUNT)) {
                argument.count = Integer.valueOf(commandLine.getOptionValue(ARG_COUNT));
            } else {
                argument.count = Arg.DEFAULT_COUNT;
            }

            if (commandLine.hasOption(ARG_SEED)) {
                argument.seed = commandLine.getOptionValue(ARG_SEED);
            }

            if (commandLine.hasOption(ARG_ENCODING)) {
                argument.encoding = commandLine.getOptionValue(ARG_ENCODING);
            } else {
                argument.encoding = Arg.DEFAULT_ENCODING;
            }

            argument.debug = commandLine.hasOption(ARG_DEBUG);
            argument.offline = commandLine.hasOption(ARG_ONLINE);

        } catch (Exception e) {
            System.err.println(e.getMessage());

            CLIParser.printHelp(options);

            argument = null;
        }

        return argument;
    }

    private static Integer parseLength(List<String> args) {
        if (!args.isEmpty() && args.get(0).matches("^-?\\d+$")) {
            return Integer.valueOf(args.get(0));
        } else {
            return Arg.DEFAULT_LENGTH;
        }
    }

    private static Options setupOptions() {
        Options options = new Options();

        Option count = Option.builder(ARG_COUNT).longOpt("count").argName("number").desc("How many randoms should be generated").hasArgs().build();
        Option encodeing = Option.builder(ARG_ENCODING).longOpt("encoding").argName("string").hasArgs().desc("What output encode should be used").build();
        Option seed = Option.builder(ARG_SEED).longOpt("seed").argName("string").hasArgs().desc("Uses the utf-8 byte representation to seed the SecureRandom.").build();
        Option debugOpt = Option.builder(ARG_DEBUG).longOpt("debug").hasArg(false).desc("Prints additional info for debugging.").build();
        Option onlineOpt = Option.builder(ARG_ONLINE).longOpt("offline").hasArg(false).desc("Skips request to Random.org to seed random generator (use when offline).").build();


        Option help = Option.builder("h").longOpt("help").desc("Prints docs").build();
        Option version = Option.builder("v").longOpt("version").desc("Prints current version.").build();

        OptionGroup mainArgs = new OptionGroup();

        options.addOptionGroup(mainArgs);
        options.addOption(count).addOption(encodeing)
                .addOption(seed).addOption(onlineOpt)
                .addOption(debugOpt).addOption(help)
                .addOption(version);

        return options;
    }

    private static void printHelp(Options options) {
        HelpFormatter help = new HelpFormatter();
        help.setWidth(120);
        help.setLeftPadding(4);
        help.setDescPadding(3);
        help.printHelp("<length> | --help", "Version:" + CmdUtil.jarVersion(), options, " ", false);
    }
}
