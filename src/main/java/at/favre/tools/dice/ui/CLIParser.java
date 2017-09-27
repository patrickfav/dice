package at.favre.tools.dice.ui;

import at.favre.tools.dice.encode.EncoderHandler;
import at.favre.tools.dice.util.MiscUtil;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;

public class CLIParser {
    static final String ARG_ENCODING = "e";
    static final String ARG_COUNT = "c";
    static final String ARG_SEED = "s";
    static final String ARG_ONLINE = "o";
    static final String ARG_URLENCODE = "u";
    static final String ARG_PADDING = "p";
    static final String ARG_DEBUG = "d";
    static final String ARG_ROBOT = "r";
    static final String ARG_CRC32 = "crc32";
    static final String ARG_OUTFILE = "f";

    public static Arg parse(String[] args) {
        Options options = setupOptions();
        CommandLineParser parser = new DefaultParser();
        Arg.Builder argument = Arg.builder();

        try {

            CommandLine commandLine = parser.parse(options, args);
            argument.length(parseLength(commandLine.getArgList()));

            if (commandLine.hasOption("h") || commandLine.hasOption("help")) {
                printHelp(options);
                return null;
            }

            if (commandLine.hasOption("v") || commandLine.hasOption("version")) {
                System.out.println(getVersion());
                return null;
            }

            if (commandLine.hasOption(ARG_COUNT)) {
                argument.count(Long.valueOf(commandLine.getOptionValue(ARG_COUNT)));
            } else {
                argument.count(null);
            }

            if (commandLine.hasOption(ARG_SEED)) {
                argument.seed(commandLine.getOptionValue(ARG_SEED));
            }

            if (commandLine.hasOption(ARG_ENCODING)) {
                argument.encoding(commandLine.getOptionValue(ARG_ENCODING));
            } else {
                argument.encoding(Arg.DEFAULT_ENCODING);
            }

            if (commandLine.hasOption(ARG_OUTFILE)) {
                String fileString = commandLine.getOptionValue(ARG_OUTFILE);
                if (fileString != null && MiscUtil.isValidPath(fileString)) {
                    argument.outFile(new File(fileString));
                }
            }

            argument.debug(commandLine.hasOption(ARG_DEBUG));
            argument.offline(commandLine.hasOption(ARG_ONLINE));
            argument.urlencode(commandLine.hasOption(ARG_URLENCODE));
            argument.padding(commandLine.hasOption(ARG_PADDING));
            argument.robot(commandLine.hasOption(ARG_ROBOT));
            argument.crc32(commandLine.hasOption(ARG_CRC32));

        } catch (Exception e) {
            System.err.println(e.getMessage());

            CLIParser.printHelp(options);

            return null;
        }

        return argument.build();
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

        Option count = Option.builder(ARG_COUNT).longOpt("count").argName("number").desc("How many randoms should be generated. Automatically chosen if this argument is omitted.").hasArgs().build();
        Option encodeing = Option.builder(ARG_ENCODING).longOpt("encoding").argName("string").hasArgs().desc("Output byte-to-text encoding. Available encodings include:\n" + new EncoderHandler().getSupportedEncodingList()).build();
        Option seed = Option.builder(ARG_SEED).longOpt("seed").argName("string").hasArgs().desc("Uses the utf-8 byte representation of given parameter to seed the internal random generator. Warns if entropy is low.").build();
        Option debugOpt = Option.builder(ARG_DEBUG).longOpt("debug").hasArg(false).desc("Prints additional info for debugging.").build();
        Option onlineOpt = Option.builder(ARG_ONLINE).longOpt("offline").hasArg(false).desc("Skips request to Random.org to seed random generator (use when offline).").build();
        Option urlencodeOpt = Option.builder(ARG_URLENCODE).longOpt("urlencode").hasArg(false).desc("Uses 'www-form-urlencoded' encoding scheme, also misleadingly known as URL encoding, on the output strings").build();
        Option paddingOpt = Option.builder(ARG_PADDING).longOpt("padding").hasArg(false).desc("If this flag is set, byte-to-text output will be padded to full byte if needed.").build();
        Option robotOpt = Option.builder(ARG_ROBOT).longOpt("robot").hasArg(false).desc("If this flag is set, output will be more friendly for scripting (ie. no verbose text, only the randoms 1 per line)").build();
        Option checksumOpt = Option.builder().longOpt(ARG_CRC32).hasArg(false).desc("If this flag is set, 4 bytes of CRC32 checksum will be appended to every random value. If you need to check the integrity of the data.").build();
        Option outFileOpt = Option.builder(ARG_OUTFILE).longOpt("file").argName("path").hasArg(true).desc("Prints the random data to given file instead of the command line. Will create the file if it does not exist or append the data if it does.").build();

        Option help = Option.builder("h").longOpt("help").desc("Shows this page.").build();
        Option version = Option.builder("v").longOpt("version").desc("Prints application version.").build();

        OptionGroup mainArgs = new OptionGroup();

        options.addOptionGroup(mainArgs);
        options.addOption(count).addOption(encodeing)
                .addOption(seed).addOption(onlineOpt)
                .addOption(urlencodeOpt).addOption(paddingOpt)
                .addOption(debugOpt).addOption(help)
                .addOption(robotOpt).addOption(checksumOpt)
                .addOption(outFileOpt).addOption(version);

        return options;
    }

    private static void printHelp(Options options) {
        HelpFormatter help = new HelpFormatter();
        help.setWidth(120);
        help.setLeftPadding(4);
        help.setDescPadding(3);
        help.printHelp("<byte-length> | --help", "Version: " + MiscUtil.jarVersion(), options, "More details: https://github.com/patrickfav/dice", false);
    }

    private static String getVersion() {
        return "Version: " + MiscUtil.jarVersion() + "\nCopyright 2017 Patrick Favre-Bulle (https://github.com/patrickfav/dice)";
    }
}
