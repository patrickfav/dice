/*
 *  Copyright 2017 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package at.favre.tools.dice.rnd;

import org.junit.Test;

public class HmacDrbgSha1NistTestVectors extends AHmacDrbgNistTestVectorsTest {

    private final static int DEFAULT_NIST_OUT_LENGTH_BIT = 640;

    @Test
    public void testHmacDrbgNistCase0() {
        byte[] entropy = hex("e91b63309e93d1d08e30e8d556906875");
        byte[] nonce = hex("f59747c468b0d0da");
        byte[] expected = hex(
                "b7928f9503a417110788f9d0c2585f8aee6fb73b220a626b3ab9825b7a9facc79723d7e1ba9255e40e65c249b6082a7bc5e3f129d3d8f69b04ed1183419d6c4f2a13b304d2c5743f41c8b0ee73225347");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase1() {
        byte[] entropy = hex("d0c57f7dc0308115b1ea30e2ea2f7702");
        byte[] nonce = hex("89cebdda617d132c");
        byte[] expected = hex("b797615a78d1afe74ebedb9d8948d82cf2bb586ed80146b96d41a709f689178b772dd342d29af5449694bf8eaf33a664a24c0ad29a12529eeaba478a799917ab4666de1b6eb2c7332017d67eea6fabd8");
        testDrbg(entropy, nonce, new byte[0], expected);
        testDrbg(entropy, nonce, null, expected);
    }

    @Test
    public void testHmacDrbgNistCase2() {
        byte[] entropy = hex("286e9d9e39e4024dea0c885fd6f7f107");
        byte[] nonce = hex("586b6a1a8ac3ac0e");
        byte[] expected = hex("ca25aa9ef286a3cd52d101db01cdf0ce14c7add124f1b6a9a8b3a48c74989baf01f6ff704da7c5d5785b6e9c21914892102313e7a15cb2f9977a513ada0d3f242819aef2c1699b72cbd358c59435101f");
        testDrbg(entropy, nonce, new byte[0], expected);
        testDrbg(entropy, nonce, null, expected);
    }

    @Test
    public void testHmacDrbgNistCase3() {
        byte[] entropy = hex("6b20dda65a96f564fc0253d38dbc290b");
        byte[] nonce = hex("813e538d040d8dd9");
        byte[] expected = hex(
                "66b6ef57a3282838dea05d122ccdfa842dda19333ded2015d381394da38c8309a6e9703ec065335b116efb97daaac9c53ceb7a218ed0db61c3ba969dc629b95f5418eadfa43c58714fb02176bc0b17ec");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase4() {
        byte[] entropy = hex("32339fc82b655051042e3038e3161c4f");
        byte[] nonce = hex("b252e495ff396be2");
        byte[] expected = hex(
                "e95e4551a37e338faae4419e3a70e4c1e3d516be7e554cabb00007c591ba7cb6c3247889a9b08e46c6619f166d996e4e34bbf6cd8a354de9964de906041f73f2ade2eb82c6e82627d3257738c2821fcb");
        testDrbg(entropy, nonce, new byte[0], expected);
    }


    @Test
    public void testHmacDrbgNistCase5() {
        byte[] entropy = hex("6b20dda65a96f564fc0253d38dbc290b");
        byte[] nonce = hex("813e538d040d8dd9");
        byte[] expected = hex(
                "66b6ef57a3282838dea05d122ccdfa842dda19333ded2015d381394da38c8309a6e9703ec065335b116efb97daaac9c53ceb7a218ed0db61c3ba969dc629b95f5418eadfa43c58714fb02176bc0b17ec");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase6() {
        byte[] entropy = hex("589766be3c03b0a351a81b1203f944e2");
        byte[] nonce = hex("928e95f8a3bc7452");
        byte[] expected = hex(
                "5bee2482667220462ac6d3c234f7333703c5abced2ff2ad91d52193e86a61cfa43be0b4f7e831e1e563e260178f23976b2f3e132356ab54567b37580bf9d751223fad7793f0ac11fc450817536116b1f");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase7() {
        byte[] entropy = hex("07cc4d22b010335045cca142d91494bf");
        byte[] nonce = hex("4d5e842af4155d17");
        byte[] expected = hex(
                "8e13a574d17dc8b44382d3b263e857f50816755917603a07ca4987fd40340042a1e6a82a227647130304d73d8704fd9ad4db3ae42daaa55b1f93948e70c451a12724fed870e02a1a8ec4eeab716c6854");
        testDrbg(entropy, nonce, new byte[0], expected);
    }


    @Test
    public void testHmacDrbgNistCase8() {
        byte[] entropy = hex("6425624a98ab3018eb4ef827f5a4fbba");
        byte[] nonce = hex("c1022d70155ef375");
        byte[] expected = hex(
                "16fd6abb10dba1659ed56d4296b65fe3f2449996bdb8eee5c94b249f04808cdd9563569a4152bd99a32592d35d6a4cc806c228284487fc1e088b178d4c8ecb6b0e3cfaacd7d39d754d8bd4e6662f44a4");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase0() {
        byte[] entropy = hex("49058e6773ed2b7ab309c0949fdf9c9e");
        byte[] nonce = hex("a457cb8ec0e7fd01");
        byte[] personalizationString = hex("dc477641d89c7fc4a30f1430197dd159");
        byte[] expected = hex(
                "4e891f4e281100453b70788929ec743a3c5edd9b81dc798bc93771368c39b612037b6f42f60c5d8924b646848151b0c295be491d4a28d1927deed523fd04d3d2dda95ed42166312e5c3392d22893b0dc");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase1() {
        byte[] entropy = hex("4ccc7d83009a28db14e839176774d45d");
        byte[] nonce = hex("9345358f336a1622");
        byte[] personalizationString = hex("e6db32976d9262b1d3dc487f22e1f5b3");
        byte[] expected = hex("5a171e9f0065ece37ba53df81ac3d88054d53d0cb695a901e1a1ca91352420b508c461ac91095ccea81621b800ddcff905020f96dad2a50377d3945047420c3b902e8e361f4525c1d4bfa8af164925d2");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase2() {
        byte[] entropy = hex("fc7d0c3ef1c404ada968dae35581b6cd");
        byte[] nonce = hex("31e0a46c39ce49dc");
        byte[] personalizationString = hex("14158a65fc9b3bc1ac04c7854493852d");
        byte[] expected = hex("918494f47dadda22667dc1d066f44f3ccbb61d3f84b2eeab7d26f4e999aab94e79d282287ab76d4e3eeeef2ef79c2ad571382abdea55d5d8642f604f8f27f3f73a5bc1413dc87bfdf91da1c6045ec223");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase3() {
        byte[] entropy = hex("1f0df7933dc99eaf7b284b02ee773ec4");
        byte[] nonce = hex("6461fd762c595408");
        byte[] personalizationString = hex("abd1d8af4ae46d7e5f1f4e0b71b54edc");
        byte[] expected = hex("f1eba7596c6c20118f86017ff86514d745ce7ea02c49719094e5c2a96d3dfa1dd5079b8eff8078ba9793900dba145a260e672837422c351c3f231c201dfaa21e48d3f7ee28bcd08dac680e80bf87ec20");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase4() {
        byte[] entropy = hex("09988a36abad74c3cf377db9c9200baf");
        byte[] nonce = hex("6c27be4e21932166");
        byte[] personalizationString = hex("17b7a40f4c37894bc948456e37ad482a");
        byte[] expected = hex("091e5fb9c6c218f2460c514fa215061460ca90cfb35c1a9f5ea125fc49aa0b2beb42dcb0fed865f8510c3141cd51d1b33216e2e72cebcabd3e1bc0eab201d8e72a0d1de1c2b7915a0cf242708092f211");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase5() {
        byte[] entropy = hex("ce1934b6561ebaaa851accf8ceae5b0d");
        byte[] nonce = hex("c587922ff68836aa");
        byte[] personalizationString = hex("602e9086f44d03ce61039c2e81fed620");
        byte[] expected = hex("441da7552b2d45533fc924ea985fd4b0b95942fc7997a37128d3e96d4c2792b241dbe921d61f3898852d4f93740cc3649cb5279a7f0f09be3990e9ee599fb0717c308e7a939a441b5c3ba0cb8aa19647");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase6() {
        byte[] entropy = hex("58f1a9eb935fd08a4c3c894a06ad00ca");
        byte[] nonce = hex("0576589700a4d50c");
        byte[] personalizationString = hex("b14f2a74cbe3881069f30507919c6870");
        byte[] expected = hex("ae9c6b40d951aab9c2d9cb920a05f3e154898c83e392dfbd7ffcbe2283eb2b75842fa5e7bd9626ad12e814874f1966fea1eb817793d2eb0a9cb9270cc9aa4267118fba0c7b6fcf487a97ebcbadc67496");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase7() {
        byte[] entropy = hex("0abf2f845295bb1dd283daa24e75fa08");
        byte[] nonce = hex("c9e9202793c479b3");
        byte[] personalizationString = hex("f8742f44932bae2d65a032ada2b76382");
        byte[] expected = hex("8847696e8edd2c7b751b780a6fc69d8434a3144593936943217465362b3c3f7b25b75149f7c69d10ecd169f00ed98b53e0e498af6d9f600441ee2c01a9e74ed845d24cdab4543dff7d1f7800a278671d");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistReseedCase0() {
        byte[] entropy = hex("79349bbf7cdda5799557866621c91383");
        byte[] nonce = hex("1146733abf8c35c8");
        byte[] reseed = hex("c7215b5b96c48e9b338c74e3e99dfedf");
        byte[] expected = hex("c6a16ab8d420706f0f34ab7fec5adca9d8ca3a133e159ca6ac43c6f8a2be22834a4c0a0affb10d7194f1c1a5cf7322ec1ae0964ed4bf122746e087fdb5b3e91b3493d5bb98faed49e85f130fc8a459b7");

        testDrbgReseed(entropy, reseed, nonce, new byte[0], new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistReseedCase1() {
        byte[] entropy = hex("ee57fc23600fb9029a9ec6c82e7b51e4");
        byte[] nonce = hex("3e9721e4393ef9ad");
        byte[] reseed = hex("841d276ca9519061d92d7ddfa6628ca3");
        byte[] expected = hex("ee26a5c8ef08a1ca8f14154d67c88f5e7ed8219d931b9842ac0039f2145539f2142b44117a998c22f590f6c9b38b465b783ecff13a7750201f7ecf1b8ab393604c73b2389336609af3440cde43298b84");

        testDrbgReseed(entropy, reseed, nonce, new byte[0], new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistReseedCase2() {
        byte[] entropy = hex("ebfdad13c8f941d279dbb4de8d7706dd");
        byte[] nonce = hex("fdaa279f5e4428d6");
        byte[] reseed = hex("f785c5b2f833b69b09b71a57cf5701d4");
        byte[] expected = hex("66e35f9b8e05a861a0b3d01c66c416d5e8b77d4d21328c625cff9163ffc92e753015aa9d7f36ae3a961681d39f271d0b627787868cec3dedc520ecb303f96a43cec67369117af268a19f5284880cb3be");

        testDrbgReseed(entropy, reseed, nonce, new byte[0], new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistReseedWithAdditionalInputCase0() {
        byte[] entropy = hex("7d7052a776fd2fb3d7191f733304ee8b");
        byte[] nonce = hex("be4a0ceedca80207");
        byte[] reseed = hex("49047e879d610955eed916e4060e00c9");
        byte[] additionalInputReseed = hex("fd8bb33aab2f6cdfbc541811861d518d");
        byte[] additional1 = hex("99afe347540461ddf6abeb491e0715b4");
        byte[] additional2 = hex("02f773482dd7ae66f76e381598a64ef0");
        byte[] expected = hex("a736343844fc92511391db0addd9064dbee24c8976aa259a9e3b6368aa6de4c9bf3a0effcda9cb0e9dc33652ab58ecb7650ed80467f76a849fb1cfc1ed0a09f7155086064db324b1e124f3fc9e614fcb");

        testDrbgReseedWithAdditionalInfo(entropy, reseed, nonce, new byte[0], additionalInputReseed, additional1, additional2, expected);
    }

    @Test
    public void testHmacDrbgNistReseedWithAdditionalInputCase1() {
        byte[] entropy = hex("29c62afa3c52208a3fdecb43fa613f15");
        byte[] nonce = hex("6c9eb59ac3c2d48b");
        byte[] reseed = hex("bd87be99d184165412314140d4027141");
        byte[] additionalInputReseed = hex("433ddaf259d14bcf897630ccaa27338c");
        byte[] additional1 = hex("141146d404f284c2d02b6a10156e3382");
        byte[] additional2 = hex("edc343dbffe71ab4114ac3639d445b65");
        byte[] expected = hex("8c730f0526694d5a9a45dbab057a1975357d65afd3eff303320bd14061f9ad38759102b6c60116f6db7a6e8e7ab94c05500b4d1e357df8e957ac8937b05fb3d080a0f90674d44de1bd6f94d295c4519d");

        testDrbgReseedWithAdditionalInfo(entropy, reseed, nonce, new byte[0], additionalInputReseed, additional1, additional2, expected);
    }

    @Test
    public void testHmacDrbgNistReseedWithAdditionalInputCase2() {
        byte[] entropy = hex("0c0d1c0328a384e697678ac87303dd62");
        byte[] nonce = hex("c8780b4ac33f1867");
        byte[] reseed = hex("4ea4dce5b190d4e381eb7a5b5e12b4f1");
        byte[] additionalInputReseed = hex("0557bc052aa8eabab0baa42ca38fbbe9");
        byte[] additional1 = hex("985865c180e0bfb7cdbed11b58b5e509");
        byte[] additional2 = hex("f40452f8c5b8f4cbc1675f70bb803740");
        byte[] expected = hex("4a1f442eae6c861b622014b079dfd47543176b82bc60826cfa02d3923ef0563f8deba8362c8d1950a70e80d67189fb4d904b855ed0ac39942aa8673e0951b4876354b849a6c1c51d0c35a3f4ed4e2f22");

        testDrbgReseedWithAdditionalInfo(entropy, reseed, nonce, new byte[0], additionalInputReseed, additional1, additional2, expected);
    }

    private void testDrbg(byte[] entropy, byte[] nonce, byte[] perso, byte[] expected) {
        testDrbg(MacFactory.Default.hmacSha1(), entropy, nonce, perso, expected, DEFAULT_NIST_OUT_LENGTH_BIT);
    }

    private void testDrbgReseed(byte[] entropy, byte[] reseed, byte[] nonce, byte[] perso, byte[] additonalInput, byte[] expected) {
        testDrbgReseed(MacFactory.Default.hmacSha1(), entropy, reseed, nonce, perso, additonalInput, null, null, expected, DEFAULT_NIST_OUT_LENGTH_BIT);
    }

    private void testDrbgReseedWithAdditionalInfo(byte[] entropy, byte[] reseed, byte[] nonce, byte[] perso, byte[] additonalInputReseed, byte[] additonalInput1, byte[] additonalInput2, byte[] expected) {
        testDrbgReseed(MacFactory.Default.hmacSha1(), entropy, reseed, nonce, perso, additonalInputReseed, additonalInput1, additonalInput2, expected, DEFAULT_NIST_OUT_LENGTH_BIT);
    }
}