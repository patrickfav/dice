# Test Report

## File

**sha256**: `c52b3fac61399806549461d17eeacbe09be5d84043f48069702fa3a9533d4527`  2017_10_01_10GB.txt

## Dice

    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [495ms] random.org [1137ms] ANU Quantum [2248ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:52:29 GMT][1.2.0] 1073741000 bytes generated in 56700 ms. (19.31 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [396ms] random.org [1241ms] ANU Quantum [1853ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:53:43 GMT][1.2.0] 1073741000 bytes generated in 55814 ms. (19.47 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [406ms] random.org [1063ms] ANU Quantum [1866ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:55:13 GMT][1.2.0] 1073741000 bytes generated in 55836 ms. (19.46 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [386ms] random.org [1124ms] ANU Quantum [1879ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:56:23 GMT][1.2.0] 1073741000 bytes generated in 56495 ms. (19.23 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [392ms] random.org [1031ms] ANU Quantum [1905ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:57:26 GMT][1.2.0] 1073741000 bytes generated in 58581 ms. (18.53 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [397ms] random.org [1090ms] ANU Quantum [1902ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:58:31 GMT][1.2.0] 1073741000 bytes generated in 55637 ms. (19.55 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [397ms] random.org [1033ms] ANU Quantum [1857ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 05:59:29 GMT][1.2.0] 1073741000 bytes generated in 55952 ms. (19.42 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [397ms] random.org [1041ms] ANU Quantum [1878ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 06:01:19 GMT][1.2.0] 1073741000 bytes generated in 55626 ms. (19.54 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [393ms] random.org [1125ms] ANU Quantum [1906ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 06:12:17 GMT][1.2.0] 1073741000 bytes generated in 56235 ms. (19.33 MB/s)
    root@DesktopPC:~# java -jar dice-1.2.0-proguard.jar 1000 -c 1073741 -e "raw" -f ./2017_10_01_10GB.txt
    Fetch external seed: Hotbits [399ms] random.org [1241ms] ANU Quantum [2184ms]

    Writing data to ./2017_10_01_10GB.txt

    [2017-10-01 06:23:34 GMT][1.2.0] 1073741000 bytes generated in 55940 ms. (19.54 MB/s)

## Dieharder

`dieharder -a -g 201 -f 2017_10_01_10GB.txt > 2017_10_01_10GB.dieharder.txt`

### Report
    #=============================================================================#
    #            dieharder version 3.31.1 Copyright 2003 Robert G. Brown          #
    #=============================================================================#
       rng_name    |           filename             |rands/second|
     file_input_raw|             2017_10_01_10GB.txt|  1.38e+07  |
    #=============================================================================#
            test_name   |ntup| tsamples |psamples|  p-value |Assessment
    #=============================================================================#
       diehard_birthdays|   0|       100|     100|0.32527207|  PASSED
          diehard_operm5|   0|   1000000|     100|0.25569639|  PASSED
      diehard_rank_32x32|   0|     40000|     100|0.56712703|  PASSED
        diehard_rank_6x8|   0|    100000|     100|0.52042035|  PASSED
       diehard_bitstream|   0|   2097152|     100|0.78686009|  PASSED
            diehard_opso|   0|   2097152|     100|0.03593008|  PASSED
            diehard_oqso|   0|   2097152|     100|0.99973898|   WEAK
             diehard_dna|   0|   2097152|     100|0.29292778|  PASSED
    diehard_count_1s_str|   0|    256000|     100|0.33718549|  PASSED
    diehard_count_1s_byt|   0|    256000|     100|0.28094538|  PASSED
     diehard_parking_lot|   0|     12000|     100|0.83699181|  PASSED
        diehard_2dsphere|   2|      8000|     100|0.90832843|  PASSED
        diehard_3dsphere|   3|      4000|     100|0.57386351|  PASSED
         diehard_squeeze|   0|    100000|     100|0.46277381|  PASSED
            diehard_sums|   0|       100|     100|0.12828687|  PASSED
            diehard_runs|   0|    100000|     100|0.11747726|  PASSED
            diehard_runs|   0|    100000|     100|0.80056598|  PASSED
           diehard_craps|   0|    200000|     100|0.85308470|  PASSED
           diehard_craps|   0|    200000|     100|0.78733086|  PASSED
     marsaglia_tsang_gcd|   0|  10000000|     100|0.24499380|  PASSED
     marsaglia_tsang_gcd|   0|  10000000|     100|0.73448103|  PASSED
             sts_monobit|   1|    100000|     100|0.86786251|  PASSED
                sts_runs|   2|    100000|     100|0.80867489|  PASSED
              sts_serial|   1|    100000|     100|0.03901563|  PASSED
              sts_serial|   2|    100000|     100|0.73485032|  PASSED
              sts_serial|   3|    100000|     100|0.38835500|  PASSED
              sts_serial|   3|    100000|     100|0.86528928|  PASSED
              sts_serial|   4|    100000|     100|0.83615209|  PASSED
              sts_serial|   4|    100000|     100|0.22748252|  PASSED
              sts_serial|   5|    100000|     100|0.74023766|  PASSED
              sts_serial|   5|    100000|     100|0.38424283|  PASSED
              sts_serial|   6|    100000|     100|0.33680341|  PASSED
              sts_serial|   6|    100000|     100|0.84081455|  PASSED
              sts_serial|   7|    100000|     100|0.23091998|  PASSED
              sts_serial|   7|    100000|     100|0.79794441|  PASSED
              sts_serial|   8|    100000|     100|0.46925887|  PASSED
              sts_serial|   8|    100000|     100|0.16753763|  PASSED
              sts_serial|   9|    100000|     100|0.73061670|  PASSED
              sts_serial|   9|    100000|     100|0.86861380|  PASSED
              sts_serial|  10|    100000|     100|0.47407673|  PASSED
              sts_serial|  10|    100000|     100|0.71397709|  PASSED
              sts_serial|  11|    100000|     100|0.22843945|  PASSED
              sts_serial|  11|    100000|     100|0.86826919|  PASSED
              sts_serial|  12|    100000|     100|0.70356988|  PASSED
              sts_serial|  12|    100000|     100|0.98810514|  PASSED
              sts_serial|  13|    100000|     100|0.05840449|  PASSED
              sts_serial|  13|    100000|     100|0.45243752|  PASSED
              sts_serial|  14|    100000|     100|0.97029939|  PASSED
              sts_serial|  14|    100000|     100|0.28646100|  PASSED
              sts_serial|  15|    100000|     100|0.99912323|   WEAK
              sts_serial|  15|    100000|     100|0.97856497|  PASSED
              sts_serial|  16|    100000|     100|0.83134831|  PASSED
              sts_serial|  16|    100000|     100|0.21316077|  PASSED
             rgb_bitdist|   1|    100000|     100|0.38337351|  PASSED
             rgb_bitdist|   2|    100000|     100|0.92720275|  PASSED
             rgb_bitdist|   3|    100000|     100|0.89992807|  PASSED
             rgb_bitdist|   4|    100000|     100|0.85563714|  PASSED
             rgb_bitdist|   5|    100000|     100|0.44050478|  PASSED
             rgb_bitdist|   6|    100000|     100|0.06413359|  PASSED
             rgb_bitdist|   7|    100000|     100|0.73919322|  PASSED
             rgb_bitdist|   8|    100000|     100|0.71983550|  PASSED
             rgb_bitdist|   9|    100000|     100|0.05529783|  PASSED
             rgb_bitdist|  10|    100000|     100|0.21505343|  PASSED
             rgb_bitdist|  11|    100000|     100|0.23019921|  PASSED
             rgb_bitdist|  12|    100000|     100|0.62660413|  PASSED
    rgb_minimum_distance|   2|     10000|    1000|0.34711937|  PASSED
    rgb_minimum_distance|   3|     10000|    1000|0.04994892|  PASSED
    rgb_minimum_distance|   4|     10000|    1000|0.26778518|  PASSED
    rgb_minimum_distance|   5|     10000|    1000|0.23521266|  PASSED
        rgb_permutations|   2|    100000|     100|0.19572071|  PASSED
        rgb_permutations|   3|    100000|     100|0.77214066|  PASSED
        rgb_permutations|   4|    100000|     100|0.09700197|  PASSED
        rgb_permutations|   5|    100000|     100|0.80095497|  PASSED
          rgb_lagged_sum|   0|   1000000|     100|0.33249739|  PASSED
          rgb_lagged_sum|   1|   1000000|     100|0.63710240|  PASSED
          rgb_lagged_sum|   2|   1000000|     100|0.09185820|  PASSED
          rgb_lagged_sum|   3|   1000000|     100|0.73700031|  PASSED
          rgb_lagged_sum|   4|   1000000|     100|0.95086818|  PASSED
          rgb_lagged_sum|   5|   1000000|     100|0.95677454|  PASSED
          rgb_lagged_sum|   6|   1000000|     100|0.02580544|  PASSED
          rgb_lagged_sum|   7|   1000000|     100|0.40424557|  PASSED
          rgb_lagged_sum|   8|   1000000|     100|0.97579043|  PASSED
          rgb_lagged_sum|   9|   1000000|     100|0.50757285|  PASSED
          rgb_lagged_sum|  10|   1000000|     100|0.64480322|  PASSED
          rgb_lagged_sum|  11|   1000000|     100|0.38762703|  PASSED
          rgb_lagged_sum|  12|   1000000|     100|0.30458527|  PASSED
          rgb_lagged_sum|  13|   1000000|     100|0.89008694|  PASSED
          rgb_lagged_sum|  14|   1000000|     100|0.99091029|  PASSED
          rgb_lagged_sum|  15|   1000000|     100|0.19509148|  PASSED
          rgb_lagged_sum|  16|   1000000|     100|0.98895998|  PASSED
          rgb_lagged_sum|  17|   1000000|     100|0.65238550|  PASSED
          rgb_lagged_sum|  18|   1000000|     100|0.28449241|  PASSED
          rgb_lagged_sum|  19|   1000000|     100|0.48068353|  PASSED
          rgb_lagged_sum|  20|   1000000|     100|0.09373559|  PASSED
          rgb_lagged_sum|  21|   1000000|     100|0.13598319|  PASSED
          rgb_lagged_sum|  22|   1000000|     100|0.93296259|  PASSED
          rgb_lagged_sum|  23|   1000000|     100|0.71855001|  PASSED
          rgb_lagged_sum|  24|   1000000|     100|0.03920395|  PASSED
          rgb_lagged_sum|  25|   1000000|     100|0.38016703|  PASSED
          rgb_lagged_sum|  26|   1000000|     100|0.66678489|  PASSED
          rgb_lagged_sum|  27|   1000000|     100|0.48197932|  PASSED
          rgb_lagged_sum|  28|   1000000|     100|0.99142471|  PASSED
          rgb_lagged_sum|  29|   1000000|     100|0.69324558|  PASSED
          rgb_lagged_sum|  30|   1000000|     100|0.80925692|  PASSED
          rgb_lagged_sum|  31|   1000000|     100|0.38657327|  PASSED
          rgb_lagged_sum|  32|   1000000|     100|0.94298843|  PASSED
         rgb_kstest_test|   0|     10000|    1000|0.75588024|  PASSED
         dab_bytedistrib|   0|  51200000|       1|0.86004981|  PASSED
                 dab_dct| 256|     50000|       1|0.77716962|  PASSED
    Preparing to run test 207.  ntuple = 0
            dab_filltree|  32|  15000000|       1|0.35870358|  PASSED
            dab_filltree|  32|  15000000|       1|0.33229542|  PASSED
    Preparing to run test 208.  ntuple = 0
           dab_filltree2|   0|   5000000|       1|0.66879119|  PASSED
           dab_filltree2|   1|   5000000|       1|0.64268682|  PASSED
    Preparing to run test 209.  ntuple = 0
            dab_monobit2|  12|  65000000|       1|0.40388251|  PASSED

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
    # The file file_input_raw was rewound 2 times
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
    # The file file_input_raw was rewound 12 times
    # The file file_input_raw was rewound 13 times
    # The file file_input_raw was rewound 14 times
    # The file file_input_raw was rewound 14 times
    # The file file_input_raw was rewound 15 times
    # The file file_input_raw was rewound 17 times
    # The file file_input_raw was rewound 18 times
    # The file file_input_raw was rewound 19 times
    # The file file_input_raw was rewound 20 times
    # The file file_input_raw was rewound 21 times
    # The file file_input_raw was rewound 22 times
    # The file file_input_raw was rewound 22 times
    # The file file_input_raw was rewound 22 times
    # The file file_input_raw was rewound 22 times
    # The file file_input_raw was rewound 22 times
    # The file file_input_raw was rewound 22 times
    # The file file_input_raw was rewound 22 times

## ENT

`ent -c 2017_10_01_10GB.txt > 2017_10_01_10GB.report.txt`

### Report

    Value Char Occurrences Fraction
      0         41941550   0.003906
      1         41941024   0.003906
      2         41948082   0.003907
      3         41936556   0.003906
      4         41931820   0.003905
      5         41936653   0.003906
      6         41945403   0.003906
      7         41970333   0.003909
      8         41932646   0.003905
      9         41948717   0.003907
     10         41955436   0.003907
     11         41943203   0.003906
     12         41946342   0.003907
     13         41942051   0.003906
     14         41941942   0.003906
     15         41950673   0.003907
     16         41951327   0.003907
     17         41943761   0.003906
     18         41939800   0.003906
     19         41939484   0.003906
     20         41941717   0.003906
     21         41945497   0.003906
     22         41931989   0.003905
     23         41950290   0.003907
     24         41943968   0.003906
     25         41941114   0.003906
     26         41935222   0.003906
     27         41944452   0.003906
     28         41945601   0.003906
     29         41932376   0.003905
     30         41940384   0.003906
     31         41951648   0.003907
     32         41933432   0.003905
     33   !     41939096   0.003906
     34   "     41940767   0.003906
     35   #     41951511   0.003907
     36   $     41941555   0.003906
     37   %     41943911   0.003906
     38   &     41942141   0.003906
     39   '     41963254   0.003908
     40   (     41935871   0.003906
     41   )     41948133   0.003907
     42   *     41942934   0.003906
     43   +     41956379   0.003907
     44   ,     41934797   0.003905
     45   -     41939722   0.003906
     46   .     41940619   0.003906
     47   /     41951817   0.003907
     48   0     41948340   0.003907
     49   1     41944319   0.003906
     50   2     41946197   0.003907
     51   3     41942810   0.003906
     52   4     41943195   0.003906
     53   5     41946241   0.003907
     54   6     41943505   0.003906
     55   7     41949312   0.003907
     56   8     41930509   0.003905
     57   9     41957850   0.003908
     58   :     41942340   0.003906
     59   ;     41934191   0.003905
     60   <     41951722   0.003907
     61   =     41940740   0.003906
     62   >     41948047   0.003907
     63   ?     41951818   0.003907
     64   @     41939698   0.003906
     65   A     41948958   0.003907
     66   B     41938043   0.003906
     67   C     41940341   0.003906
     68   D     41944244   0.003906
     69   E     41937656   0.003906
     70   F     41941860   0.003906
     71   G     41953877   0.003907
     72   H     41935043   0.003906
     73   I     41952277   0.003907
     74   J     41927049   0.003905
     75   K     41945211   0.003906
     76   L     41945714   0.003907
     77   M     41946129   0.003907
     78   N     41942426   0.003906
     79   O     41942286   0.003906
     80   P     41942822   0.003906
     81   Q     41940477   0.003906
     82   R     41955163   0.003907
     83   S     41934009   0.003905
     84   T     41931325   0.003905
     85   U     41943392   0.003906
     86   V     41934812   0.003905
     87   W     41932365   0.003905
     88   X     41947805   0.003907
     89   Y     41941386   0.003906
     90   Z     41947792   0.003907
     91   [     41948349   0.003907
     92   \     41941599   0.003906
     93   ]     41928092   0.003905
     94   ^     41938109   0.003906
     95   _     41943543   0.003906
     96   `     41933185   0.003905
     97   a     41942240   0.003906
     98   b     41959582   0.003908
     99   c     41934878   0.003905
    100   d     41944244   0.003906
    101   e     41934704   0.003905
    102   f     41959230   0.003908
    103   g     41944395   0.003906
    104   h     41946600   0.003907
    105   i     41943860   0.003906
    106   j     41940869   0.003906
    107   k     41943543   0.003906
    108   l     41949274   0.003907
    109   m     41944594   0.003906
    110   n     41941292   0.003906
    111   o     41941411   0.003906
    112   p     41934624   0.003905
    113   q     41940284   0.003906
    114   r     41947582   0.003907
    115   s     41952289   0.003907
    116   t     41948123   0.003907
    117   u     41936826   0.003906
    118   v     41940857   0.003906
    119   w     41950737   0.003907
    120   x     41950762   0.003907
    121   y     41931396   0.003905
    122   z     41943482   0.003906
    123   {     41941646   0.003906
    124   |     41937620   0.003906
    125   }     41943511   0.003906
    126   ~     41956655   0.003908
    127         41941576   0.003906
    128         41944297   0.003906
    129         41948437   0.003907
    130         41950756   0.003907
    131         41945106   0.003906
    132         41957645   0.003908
    133         41947702   0.003907
    134         41947041   0.003907
    135         41939854   0.003906
    136         41930072   0.003905
    137         41950735   0.003907
    138         41940633   0.003906
    139         41938968   0.003906
    140         41932895   0.003905
    141         41940753   0.003906
    142         41956629   0.003908
    143         41949610   0.003907
    144         41956738   0.003908
    145         41954264   0.003907
    146         41942298   0.003906
    147         41935845   0.003906
    148         41947480   0.003907
    149         41944471   0.003906
    150         41950855   0.003907
    151         41951215   0.003907
    152         41937947   0.003906
    153         41943371   0.003906
    154         41940633   0.003906
    155         41947803   0.003907
    156         41942441   0.003906
    157         41948575   0.003907
    158         41938937   0.003906
    159         41933513   0.003905
    160         41937634   0.003906
    161        41937098   0.003906
    162        41934337   0.003905
    163        41938100   0.003906
    164        41943580   0.003906
    165        41949692   0.003907
    166        41935107   0.003906
    167        41945472   0.003906
    168        41948914   0.003907
    169        41939239   0.003906
    170        41938549   0.003906
    171        41939336   0.003906
    172        41947788   0.003907
    173        41945472   0.003906
    174        41950597   0.003907
    175        41943909   0.003906
    176        41943741   0.003906
    177        41940175   0.003906
    178        41946243   0.003907
    179        41940418   0.003906
    180        41947390   0.003907
    181        41936967   0.003906
    182        41943539   0.003906
    183        41953370   0.003907
    184        41925559   0.003905
    185        41942664   0.003906
    186        41939413   0.003906
    187        41936031   0.003906
    188        41928800   0.003905
    189        41937957   0.003906
    190        41936783   0.003906
    191        41947284   0.003907
    192        41935053   0.003906
    193        41939395   0.003906
    194        41944066   0.003906
    195        41948326   0.003907
    196        41942679   0.003906
    197        41939162   0.003906
    198        41935627   0.003906
    199        41955362   0.003907
    200        41940695   0.003906
    201        41942453   0.003906
    202        41950449   0.003907
    203        41946050   0.003907
    204        41939004   0.003906
    205        41936854   0.003906
    206        41933150   0.003905
    207        41954082   0.003907
    208        41946917   0.003907
    209        41934511   0.003905
    210        41939683   0.003906
    211        41944418   0.003906
    212        41943622   0.003906
    213        41947072   0.003907
    214        41939907   0.003906
    215        41940571   0.003906
    216        41944723   0.003906
    217        41951579   0.003907
    218        41945534   0.003906
    219        41952236   0.003907
    220        41939200   0.003906
    221        41945392   0.003906
    222        41947224   0.003907
    223        41941322   0.003906
    224        41944578   0.003906
    225        41942595   0.003906
    226        41947719   0.003907
    227        41941086   0.003906
    228        41933295   0.003905
    229        41942898   0.003906
    230        41941708   0.003906
    231        41939746   0.003906
    232        41948213   0.003907
    233        41942699   0.003906
    234        41937766   0.003906
    235        41937548   0.003906
    236        41943331   0.003906
    237        41935710   0.003906
    238        41940244   0.003906
    239        41936664   0.003906
    240        41948242   0.003907
    241        41938388   0.003906
    242        41941677   0.003906
    243        41943910   0.003906
    244        41943604   0.003906
    245        41942620   0.003906
    246        41929365   0.003905
    247        41934937   0.003905
    248        41933446   0.003905
    249        41945587   0.003906
    250        41943303   0.003906
    251        41939536   0.003906
    252        41949158   0.003907
    253        41937326   0.003906
    254        41943535   0.003906
    255        41942712   0.003906

    Total:    10737410000   1.000000

    Entropy = 8.000000 bits per byte.

    Optimum compression would reduce the size
    of this 10737410000 byte file by 0 percent.

    Chi square distribution for 10737410000 samples is 274.29, and randomly
    would exceed this value 19.42 percent of the times.

    Arithmetic mean value of data bytes is 127.4989 (127.5 = random).
    Monte Carlo value for Pi is 3.141552023 (error 0.00 percent).
    Serial correlation coefficient is 0.000004 (totally uncorrelated = 0.0).

