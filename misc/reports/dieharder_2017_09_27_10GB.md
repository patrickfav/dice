# Test Report

## File

**sha256**: `63f6ec51580462cefff8a5b23d5f78f74f90750e8f650f911b6744df5be87d70`  /mnt/c/Users/PatrickF/2017_09_27_10GB.txt

## Dice

`1024 -c 10737418 -e "raw" -f "C:\Users\PatrickF\2017_09_27_10GB.txt""`

    Fetch external seed: Hotbits [511ms] random.org [1912ms] ANU Quantum [2086ms]

    Writing data to C:\Users\PatrickF\2017_09_27_10GB.txt

    [2017-09-27 21:31:18 MESZ][410de9c] -1889785856 bytes generated in 415718 ms.

## Dieharder

`dieharder -a -g 201 -f "/mnt/c/Users/PatrickF/2017_09_27_10GB.txt" > "/mnt/c/Users/PatrickF/2017_09_27_10GB.report.txt"`

### Report

    #=============================================================================#
    #            dieharder version 3.31.1 Copyright 2003 Robert G. Brown          #
    #=============================================================================#
       rng_name    |           filename             |rands/second|
     file_input_raw|/mnt/c/Users/PatrickF/2017_09_27_10GB.txt|  1.98e+07  |
    #=============================================================================#
            test_name   |ntup| tsamples |psamples|  p-value |Assessment
    #=============================================================================#
       diehard_birthdays|   0|       100|     100|0.37513987|  PASSED
          diehard_operm5|   0|   1000000|     100|0.58472933|  PASSED
      diehard_rank_32x32|   0|     40000|     100|0.07882220|  PASSED
        diehard_rank_6x8|   0|    100000|     100|0.24111253|  PASSED
       diehard_bitstream|   0|   2097152|     100|0.62286981|  PASSED
            diehard_opso|   0|   2097152|     100|0.40887931|  PASSED
            diehard_oqso|   0|   2097152|     100|0.45945928|  PASSED
             diehard_dna|   0|   2097152|     100|0.22507411|  PASSED
    diehard_count_1s_str|   0|    256000|     100|0.38658205|  PASSED
    diehard_count_1s_byt|   0|    256000|     100|0.75490738|  PASSED
     diehard_parking_lot|   0|     12000|     100|0.64976124|  PASSED
        diehard_2dsphere|   2|      8000|     100|0.37785573|  PASSED
        diehard_3dsphere|   3|      4000|     100|0.19643978|  PASSED
         diehard_squeeze|   0|    100000|     100|0.97479180|  PASSED
            diehard_sums|   0|       100|     100|0.24502296|  PASSED
            diehard_runs|   0|    100000|     100|0.59001996|  PASSED
            diehard_runs|   0|    100000|     100|0.69434726|  PASSED
           diehard_craps|   0|    200000|     100|0.98016166|  PASSED
           diehard_craps|   0|    200000|     100|0.75340342|  PASSED
     marsaglia_tsang_gcd|   0|  10000000|     100|0.45939969|  PASSED
     marsaglia_tsang_gcd|   0|  10000000|     100|0.76681923|  PASSED
             sts_monobit|   1|    100000|     100|0.95603023|  PASSED
                sts_runs|   2|    100000|     100|0.73514834|  PASSED
              sts_serial|   1|    100000|     100|0.76005921|  PASSED
              sts_serial|   2|    100000|     100|0.62352811|  PASSED
              sts_serial|   3|    100000|     100|0.44589369|  PASSED
              sts_serial|   3|    100000|     100|0.48575434|  PASSED
              sts_serial|   4|    100000|     100|0.63756523|  PASSED
              sts_serial|   4|    100000|     100|0.95503415|  PASSED
              sts_serial|   5|    100000|     100|0.77755543|  PASSED
              sts_serial|   5|    100000|     100|0.73578657|  PASSED
              sts_serial|   6|    100000|     100|0.48398215|  PASSED
              sts_serial|   6|    100000|     100|0.30373206|  PASSED
              sts_serial|   7|    100000|     100|0.74043601|  PASSED
              sts_serial|   7|    100000|     100|0.59843100|  PASSED
              sts_serial|   8|    100000|     100|0.50602783|  PASSED
              sts_serial|   8|    100000|     100|0.80525802|  PASSED
              sts_serial|   9|    100000|     100|0.94452743|  PASSED
              sts_serial|   9|    100000|     100|0.84634157|  PASSED
              sts_serial|  10|    100000|     100|0.44451352|  PASSED
              sts_serial|  10|    100000|     100|0.99617834|   WEAK
              sts_serial|  11|    100000|     100|0.25304790|  PASSED
              sts_serial|  11|    100000|     100|0.63071971|  PASSED
              sts_serial|  12|    100000|     100|0.79915368|  PASSED
              sts_serial|  12|    100000|     100|0.68980659|  PASSED
              sts_serial|  13|    100000|     100|0.98039648|  PASSED
              sts_serial|  13|    100000|     100|0.31288774|  PASSED
              sts_serial|  14|    100000|     100|0.81040116|  PASSED
              sts_serial|  14|    100000|     100|0.97334516|  PASSED
              sts_serial|  15|    100000|     100|0.42012719|  PASSED
              sts_serial|  15|    100000|     100|0.99733854|   WEAK
              sts_serial|  16|    100000|     100|0.50624047|  PASSED
              sts_serial|  16|    100000|     100|0.55246536|  PASSED
             rgb_bitdist|   1|    100000|     100|0.74835039|  PASSED
             rgb_bitdist|   2|    100000|     100|0.56092621|  PASSED
             rgb_bitdist|   3|    100000|     100|0.84767947|  PASSED
             rgb_bitdist|   4|    100000|     100|0.14656644|  PASSED
             rgb_bitdist|   5|    100000|     100|0.80400380|  PASSED
             rgb_bitdist|   6|    100000|     100|0.41314176|  PASSED
             rgb_bitdist|   7|    100000|     100|0.35248157|  PASSED
             rgb_bitdist|   8|    100000|     100|0.21797615|  PASSED
             rgb_bitdist|   9|    100000|     100|0.74613085|  PASSED
             rgb_bitdist|  10|    100000|     100|0.96110109|  PASSED
             rgb_bitdist|  11|    100000|     100|0.45748839|  PASSED
             rgb_bitdist|  12|    100000|     100|0.01679178|  PASSED
    rgb_minimum_distance|   2|     10000|    1000|0.89129546|  PASSED
    rgb_minimum_distance|   3|     10000|    1000|0.72195313|  PASSED
    rgb_minimum_distance|   4|     10000|    1000|0.04337471|  PASSED
    rgb_minimum_distance|   5|     10000|    1000|0.00871146|  PASSED
        rgb_permutations|   2|    100000|     100|0.59946524|  PASSED
        rgb_permutations|   3|    100000|     100|0.52490219|  PASSED
        rgb_permutations|   4|    100000|     100|0.99469959|  PASSED
        rgb_permutations|   5|    100000|     100|0.95665864|  PASSED
          rgb_lagged_sum|   0|   1000000|     100|0.76318016|  PASSED
          rgb_lagged_sum|   1|   1000000|     100|0.25831496|  PASSED
          rgb_lagged_sum|   2|   1000000|     100|0.79234965|  PASSED
          rgb_lagged_sum|   3|   1000000|     100|0.49622154|  PASSED
          rgb_lagged_sum|   4|   1000000|     100|0.62067107|  PASSED
          rgb_lagged_sum|   5|   1000000|     100|0.87313205|  PASSED
          rgb_lagged_sum|   6|   1000000|     100|0.71144558|  PASSED
          rgb_lagged_sum|   7|   1000000|     100|0.95676634|  PASSED
          rgb_lagged_sum|   8|   1000000|     100|0.68072969|  PASSED
          rgb_lagged_sum|   9|   1000000|     100|0.41208001|  PASSED
          rgb_lagged_sum|  10|   1000000|     100|0.38182948|  PASSED
          rgb_lagged_sum|  11|   1000000|     100|0.67137209|  PASSED
          rgb_lagged_sum|  12|   1000000|     100|0.43109249|  PASSED
          rgb_lagged_sum|  13|   1000000|     100|0.35679464|  PASSED
          rgb_lagged_sum|  14|   1000000|     100|0.74859957|  PASSED
          rgb_lagged_sum|  15|   1000000|     100|0.67451450|  PASSED
          rgb_lagged_sum|  16|   1000000|     100|0.63398192|  PASSED
          rgb_lagged_sum|  17|   1000000|     100|0.95109681|  PASSED
          rgb_lagged_sum|  18|   1000000|     100|0.91054655|  PASSED
          rgb_lagged_sum|  19|   1000000|     100|0.71164017|  PASSED
          rgb_lagged_sum|  20|   1000000|     100|0.16525756|  PASSED
          rgb_lagged_sum|  21|   1000000|     100|0.78849027|  PASSED
          rgb_lagged_sum|  22|   1000000|     100|0.99108222|  PASSED
          rgb_lagged_sum|  23|   1000000|     100|0.96478584|  PASSED



### Warnings

    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 1 times
    # The file file_input_raw was rewound 2 times
    # The file file_input_raw was rewound 2 times
    # The file file_input_raw was rewound 2 times
    # The file file_input_raw was rewound 2 times
    # The file file_input_raw was rewound 2 times
    # The file file_input_raw was rewound 3 times
    # The file file_input_raw was rewound 3 times
    # The file file_input_raw was rewound 3 times
    # The file file_input_raw was rewound 4 times
    # The file file_input_raw was rewound 4 times
    # The file file_input_raw was rewound 5 times
    # The file file_input_raw was rewound 5 times
    # The file file_input_raw was rewound 6 times
    # The file file_input_raw was rewound 6 times
    # The file file_input_raw was rewound 7 times
    # The file file_input_raw was rewound 8 times
    # The file file_input_raw was rewound 8 times
    # The file file_input_raw was rewound 9 times
    # The file file_input_raw was rewound 10 times
    # The file file_input_raw was rewound 11 times
    # The file file_input_raw was rewound 11 times
    # The file file_input_raw was rewound 12 times