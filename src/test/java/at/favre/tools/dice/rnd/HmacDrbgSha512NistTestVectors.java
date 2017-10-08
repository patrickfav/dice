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

public class HmacDrbgSha512NistTestVectors extends AHmacDrbgNistTestVectorsTest {

    @Test
    public void testHmacDrbgNistCase0() {
        byte[] entropy = hex("35049f389a33c0ecb1293238fd951f8ffd517dfde06041d32945b3e26914ba15");
        byte[] nonce = hex("f7328760be6168e6aa9fb54784989a11");
        byte[] expected = hex(
                "e76491b0260aacfded01ad39fbf1a66a88284caa5123368a2ad9330ee48335e3c9c9ba90e6cbc9429962d60c1a6661edcfaa31d972b8264b9d4562cf18494128a092c17a8da6f3113e8a7edfcd4427082bd390675e9662408144971717303d8dc352c9e8b95e7f35fa2ac9f549b292bc7c4bc7f01ee0a577859ef6e82d79ef23892d167c140d22aac32b64ccdfeee2730528a38763b24227f91ac3ffe47fb11538e435307e77481802b0f613f370ffb0dbeab774fe1efbb1a80d01154a9459e73ad361108bbc86b0914f095136cbe634555ce0bb263618dc5c367291ce0825518987154fe9ecb052b3f0a256fcc30cc14572531c9628973639beda456f2bddf6");
        testDrbg(entropy, nonce, new byte[0], expected);
        testDrbg(entropy, nonce, null, expected);
    }

    @Test
    public void testHmacDrbgNistCase1() {
        byte[] entropy = hex("4cc8214cd7e85a76bfa735bbbfce926c0323fc348de6c05ed1800c2c8f58c6b1");
        byte[] nonce = hex("001eb1f6b29b35242a3f8fa2e90003f4");
        byte[] expected = hex(
                "1efa15d644e1bdf34eade3ff2f5e9ca45203ccaa1e534ac9b4287a846b71292b03102286d99f2be64b898fe909238f540ebc25f49522f60ef723a4c428ead530a97c62405cd5d9ecc54ac5baa47ac4f6195d637833f462d21a659b4903d9cfa6c9fd4512445f9abb5782899a6bb64592f3c2b3c745b18645301fdb09a6a331e9fb6d9654fc79c14ed83ac1684c755b9cb209885f86ff290a71f08a848b960152f05b1aa8566bd382ddd45521062831d7a0fb3a8bd8e112a91b5960690cd8585c1aa104514e3b9cbf52f6384e84c27bda2802fe9fb952cbf2bd607f869d0aeaa6b136c6a5f6e9b0522b6019b7ba6af6cff99fda612e024867decd8c0c6fde2034");
        testDrbg(entropy, nonce, new byte[0], expected);
        testDrbg(entropy, nonce, null, expected);
    }

    @Test
    public void testHmacDrbgNistCase2() {
        byte[] entropy = hex("d046270e6b7997cd5f4e9ed1193e55382191f78547a660854cf60bb03d039a39");
        byte[] nonce = hex("50cd147a3445f6d32d14cbfb9da0c327");
        byte[] expected = hex(
                "cdfa9441aa5eb11fe3ba50528ed731c9ff9e70b78da075d00c52d0e281e3a868f66a53a2a6a272d7e0b1a32b6339f8afd108bb9e66b04c3d6bc069b7e01b69844322df7deac66e605a9e2f43665b7932c67c418a77a4c9a302782d0e735795755613a1c5e90089f759d780fb3a984dee4e06ba3dc5a8c652549587d975e586a98ac6aba6563e2767f1a379261b9dd37992ea9681881ea7933b5c64093234c849142ced85bbe5956f527d46ef091e4d18df2a6102621a91bca51bf7aa4b242414dc16e74ae59dfe560c19dbe315e7f98b11086bc26e336dcefcb91c4828682da90d3921336a45fcd36ea4d1213a13213a132bf20aa1a3991b60b65de7ab9cc656");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase3() {
        byte[] entropy = hex("8c7c80b169160c78104c205e4492a9477e6f7ba1c3bb4daa86d222deb6241bfd");
        byte[] nonce = hex("2d2dcd5c40b46fa553ca6a2f6be96991");
        byte[] expected = hex(
                "1658a7552e4cc98c228072801f9ba230123e7f1f7dca7ba839f440e5f7570fd29c38b86a2aaca04cc87a56205b41d19e38998b47d0ffbfbd9bb56a6eb31bbfdce8d01e8991b82315c39f60c222298160e8d9f14b1a6038d8eaf15eb7310b180a8e2e8d05ef028782b55d4782d4774160a39896d1a896823f8b92a99abb546ef02cf189200a1a7a2fbb7019e4d8a935224c20d11a18e0d8890549666f6599c261532b036051cf7a65dd33bc0aeab8fa2ac9ed520f6dd893b9dc3cd3b87d02a0543eca0bb52c58b7ac4ab3f00171e21dfd3363229ed362f960d8a5fd06af5caa86018f9dce81ade6234a6992bfb9e2660d08a103dadd7d9ade4c45d691aa3799c1");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase4() {
        byte[] entropy = hex("cd394508d86c384c0c998b58cf7017b7124269428e4cf39519b5815cc2d88734");
        byte[] nonce = hex("fd2cbc87c79063db588d90b9cb1569f3");
        byte[] expected = hex(
                "7c4de5fa97362e63e52e790fb66d4b067e8cc1742975ba6f9186295832d31c4e0c97b7dffa262b93b109621044a4bc89c9fc82211f5cb763974eb3a816fa7d7853577bee1c36c2c36aabe28559d5bd85691c3e3bd610e61e4c3b76e167526d1331459d8bf09ceb403062cc97e1229eb3a70af6049d291aadb002786e7d21b81c87fa68a51a1b9a89394788bab70783a88c883ca17eceaba455f357c611fb279e38f67e3c27c5ade5f95721fa79fc2da1bd44ca7f304161359da4e45d7b847672bc185ba502123a802535dbd167b2c93bf901626e23fcaba03c4f89625a930caaaa30400645680e5931e094aac6f6467b90b13c2be9c98744f89d113151cd2ffb");
        testDrbg(entropy, nonce, new byte[0], expected);
    }


    @Test
    public void testHmacDrbgNistCase5() {
        byte[] entropy = hex("a14be417001030f6a9c543f829715b075d0efd8fa35acc7eed02a1401c6f59df");
        byte[] nonce = hex("c87b8b9255e62fcda6a35e52fa4a6f9d");
        byte[] expected = hex(
                "ed29a49be56e081f5b6abcd2ca1a16dc096071989de72a39b8bd544d2a2a2db5c886c0c29ce454cf60addb56cb4f28f959ccb7163280ef81e48dd2a02024c34a120301d359f03844d1af01f485afbe7c9b17288cf345172290fdc44e124670c5ca9e8590df6f9f63d60849c62d3921003532dbe3e3e6bdd75d28211365f6c489598a99e605ca671ff91552b5916ea9e12259723c0e1a633be34932d0c816c30b519c79656a70368b28fadaf5eb32eb6e47e00b04f152ace2eafc9a3ebd3b1b3795ad85e0897e46ab57c361fef2908041d365f73180b505ae2426603decd0b7dd33e2f7ac885aced4194999602d4d62a984233d0696fff86f7fa7a6cf993fb7e5");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase6() {
        byte[] entropy = hex("b8ceee088f3b13dbd1e7cf230449f246a456f504d63fd4288838a50ab76576a3");
        byte[] nonce = hex("f400502913cf57cb2341c5e6a63fe9fa");
        byte[] expected = hex(
                "b4fe3f6caedf4ac7b93fb1c2f316bafa58487f28a37b8400fd1f32c963b04cb3c7eb601d0dd8a7e4538b14030fb0e97794c617366ca827e3afdb0f714983a6a72b261db8bf98d5fc48fb55158661f987d08e952913212717cf204a3e8cf1177f63e2a46d920ffcec4b580a1361253a689bf765200f4e90dc6b34a56e10cfdbf932fbc3b75da1d55cba0c5287f552d883763b83acdfc7fc9d762f79774701f7ace701f0b26c67217e022bf6b6e0602e0d68cb1377b5ebccb9a8e41188dd1dea662663e8aa093787d6490a4e887a34a27309c64c40e4ab2f0acfec4a1b8d419d99fb578aaa82da9166a7d7873e27226db20d313e868bcfa4fe3854d6fb34def7d6");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistCase7() {
        byte[] entropy = hex("3c1e8a0199786fc268ee0ca0c0446d7363bd781069cf3a3faef2592cba06ce1e");
        byte[] nonce = hex("70c7c691af73d6d59addbd6e3f646d64");
        byte[] expected = hex(
                "06f44bebc2c1736b5cee283e530bb877b28651d70983c272a10efa80e3794ee428644048d67245dd3ca8b769b6bb192c9468a9fcf2b71c417283713d39e800225ba659c3273022f5177fd7867173f457f3bb66ff2c2e7bb2574dfee54438e35c98506c178d35259b04e7c541016f5c2d980074b4ea865203ae2e8935d745a02ab5cce04d233cbc18719b1900f2e7e98229b851d19fac02fa6e5ac1bc973b20a17509739bd989d4ef5a66fd9e19e3ceef2415b498843e93631b2b168167bdbb8db313eef4c9668d5001cb34767ee41db872163987c3bdc144637b52dcb767ffc19bf44fbad487b1eeae7957b497fd59a95f0988315eba73ab7206542f31c49267");
        testDrbg(entropy, nonce, new byte[0], expected);
    }


    @Test
    public void testHmacDrbgNistCase8() {
        byte[] entropy = hex("e8a0925bfce66dee7e6a54fe0311d259bd7f7a22b8576d64840cc51c731212cb");
        byte[] nonce = hex("1763365deab3ab82de9996e5c8570eb9");
        byte[] expected = hex(
                "63ddfd70508cfa247408ec231d56df7905f65b62e5e5a8309fff5239620faa0f055d7b8fdbc648ded78fd567c3141e1723d197296c92d43fdc18af0c4a54fcd52613286c78ba7bdfd0fcacc7b11b374739088323ba95f30872d77b6aad21228253133d76d29d0d742ba349956fe71e8bbf3fc7186a3f85f144a9040ceb0529a713583c1fcdee756d0130b38df0964bfc3b669fabb6ec6874d17d9ecda9fa567890e42540185eeb3497ba8db80b803f63803442aec14735e9eda177484ad61bf0c76c2862b7691b4cc74efbe35203f8cf4f24aaaa1d831030f28eef8b49e85b249e6fe835964d53aa74de6a31424ec3c833f4b8b39559934bf5f23d4b1d450bc3");
        testDrbg(entropy, nonce, new byte[0], expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase0() {
        byte[] entropy = hex("73529bba71a3d4b4fcf9a7edeed269dbdc3748b90df68c0d00e245de54698c77");
        byte[] nonce = hex("22e2d6e24501212b6f058e7c54138007");
        byte[] personalizationString = hex("e2cc19e31595d0e4de9e8bd3b236dec2d4b032c3dd5bf9891c284cd1bac67bdb");
        byte[] expected = hex(
                "1a73d58b7342c3c933e3ba15eedd8270988691c3794b45aa358570391571881c0d9c4289e5b198db5534c3cb8466ab48250fa67f24cb19b7038e46af56687bab7e5de3c82fa7312f54dc0f1dc93f5b03fcaa6003cae28d3d4707368c144a7aa46091822da292f97f32caf90ae3dd3e48e808ae12e633aa0410106e1ab56bc0a0d80f438e9b3492e4a3bc88d73a3904f7dd060c48ae8d7b12bf89a19551b53b3f55a511d2820e941640c845a8a0466432c5850c5b61bec5272602521125addf677e949b96782bc01a904491df08089bed004ad56e12f8ea1a200883ad72b3b9fae12b4eb65d5c2bacb3ce46c7c48464c9c29142fb35e7bc267ce852296ac042f9");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase1() {
        byte[] entropy = hex("e72d696d4dcb41a42d89037487b3eb01d2ddcf0407e4daa0b8ff562461eb6f83");
        byte[] nonce = hex("b2b01fb9732601f3573c6831aef09ac3");
        byte[] personalizationString = hex("8bbb235642f8caed49700463168ced9971a0204b981025bee41c72b76a965d51");
        byte[] expected = hex("8e5cd2a91ea113072623b787ed37454fae2c1905a725be3b124bb243057794dd68325a5d8bba5d6c7796d91cf682587ebe91f6beb33c5cd60b00940bf6de57c7fb082b9ba0e5b44e021be6d45099ea59367291a733ca50334f0315d52ed267af1b98e5be59359a0b9965727d92aafe4c92fda2c915cfb5a378c7a717f743a3524fddf5a48b1e7e113fa6e94b76f4e13fce3cc18b970f2ed1c34bd622611c979b6a9712c4447c3e50a7bb7ae30a8bbf479dd4242a8c4d1492c892107bb4ca695e3c790c9799bc6ffd563ebc3a4968accfc2085ab576ca6e9bfb2904a357c2cfddb55378994e5e4b36008cacaae024312fdf036a097f0fc1d3f9e75fe7cff74540");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase2() {
        byte[] entropy = hex("8d6ff3e3fe6808bc796a73957b19d758b257fab71dbb4e39acc2fafb01f88e98");
        byte[] nonce = hex("2e2b74490e062044975d6b1885f61ec7");
        byte[] personalizationString = hex("b5f819154ed5442d5547771af76cb6333d7f35e6f3e1cb03b22d09e601eb8eec");
        byte[] expected = hex("9cb257ba9d77b95e5bb2305424116eedc126b9a8654e56942e282da3823c09d9cc1c4b9c4975f13f9138e93ac75c4bd552c81d41d6363466cf2a38e2eec9294a93630dbc9d7ca89a0725fc7e41b0e55e8999279df88fb579dcc500aaa97d78ae354496048ad47d0b0285bac5e5f6e9bd50c2d9bb6f06090b3835171533d2105fbbb3762b2e33bb75c5f7c89cfb809858244526f8de3aa4f611bde41059109e8ff21b81392b55b9867523b4a4cf5dd74c23a2066f9b26225cace5c5822888f7f7dd233816ec3ef137852945fc6648b4c7cf11fa744cdf62aeb02ec2e68c29c071c84883472619da2680068aec82700cc1cec0dbae8b740b3be7c4b6c972c62998");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase3() {
        byte[] entropy = hex("138a1a9408cd7de6c1796910145263e5816d377cb9949bad0927f9c61e7fd18c");
        byte[] nonce = hex("71eb585159831189707ac6b8b6f80100");
        byte[] personalizationString = hex("9c0ca722c55c19694df96e70f941b5b230efb1fccc7a7abe0c471dde92a39272");
        byte[] expected = hex("976e1a19af964502177d27b09458e3beca6e9082ce21721d31b7241795886213894f2e3f9acc23af6aa5eae7218d00871dac9e237cde1dd9654c1e893bac47e9444f4dabd26d3dcd9f11a68b297956f135df32fc3470f7b01383c506e2a2fbdf1c387c4ce684592fd4e5be0469a29c8031cd1ccd002edae3bea44c93695241296d472783df68e5ac7a62e33a5a74f70fe45252f88309b7f3603f43a5a0a823cf6f3bd8670d50663ecc326aed709224b60fe67ed29860b31c8161d416dc5cbca1c16e69cd7ce41a77cd465cb810f2b5963d15e352715fd5e6175c68125fc8e107d6573ea89db8b34fdf93e2e173cef77746e6142c8e5c44dc288ce86c0710a04b");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase4() {
        byte[] entropy = hex("f4010441edc99d524f6caa660c180e25afdd09315f2a495ec003471e1f79bf0a");
        byte[] nonce = hex("13bc6f04d81b25f4d4c9321aa3fa58b4");
        byte[] personalizationString = hex("37fbbb3fc5aa698084184ecc8e8b45212dcf851e6e4f2db8f775d3f759ef0c4c");
        byte[] expected = hex("cb3a853a930cce84703c47719f6d609fce15f903a887f646c0781606f6f926765afd7411b4fc44be350b14cfff04383e4930eb36f94329033cd5f21d467fa74ca1a3c61c94a290bf6753809d8e7b75431d9450361613b16d6be55c392cbcb3d4212ea42cb341878cb268f3daa87c73297a807e35b82458cab693ec9bf66f43328ff1c4e727998d841a1d1491ad55c08522e5c69f19233fb4a8b11e937c55afa38ef5e0ac74b6f856902501a2558663f453d069c291d762da968298d596104580d80bf0787ce4a148dcda5ffc4b47c653f9b7bcde6cbd36dce707738b114d5f6e488b17b63f402c99d0c2b4dca40ef3d05025e50f87fbad86468ed5c08599bfdb");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase5() {
        byte[] entropy = hex("64179b56a75ce27513f0353e10a5107b935293af96799112448fb5787d9949d0");
        byte[] nonce = hex("f38d2f6e523ca3ef7429621db65a37d6");
        byte[] personalizationString = hex("f0cfd2400dd8d5e857e5878fc811a5c9ce5e0097184ccdc57c0f96d5a66020c3");
        byte[] expected = hex("b79591df35bf97c7f4731ded3eefcaaf9d0033bbb0741c0c28aca622e211491107ba9b9645ed325ae18a7aac6160cee4e34f3d3a44de58af0f5fe148b3c93fa37fd2f98e1c3c46a216bfb78d2636dc6b89501ddc315ca16eaa31a818b2fd100a47ade02234096ecdbdfd0997a3c7a4481815665b9a41070bb1bd4d7d9576957421d3b9b75d0e06419298aaacaa4f77afccecc7a67d59295aa0122197ba72b115b8d6960fa7c6c801785734f716b36805b97d4416baa1ab16c190bb8095b0c9e1267f1ade79791333ef6e442c681134dc1ff4ea547a89b84fee3bee0cb481f0afc3d1d2026add34c44f67dfce9c01ffcd443d0d5eaea00b60d0cb2ef4fad1dbef");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase6() {
        byte[] entropy = hex("c21f2ca0d103186755dbf2d8470c0282fe11824f0873cfd7935671d3af749ebe");
        byte[] nonce = hex("961df3048132b35d39bc1f96e21672b3");
        byte[] personalizationString = hex("d040489ad6f484b13be395fd99fff34706cbf96867e614cd6c91554d61231eee");
        byte[] expected = hex("a91bc36569a590e5dba3b64e896b21625a8555f71ab77f6f348d2d787e19594046a255f265bee7171100f2db39e3a5d966fcdd88ebc127b23b1798da44e03006a1987a4f5c8af3d83c220661b3c1ac45c5e785d96075681b2b19da1d5b0d143f3cf43c8010523f08feb59360d8a3dc12f101f2542d9fb0161696914f96cd1db573fde8957da6d7e9821032d9fca00f657c326ec0418cc3c60ae387e13894126fce27573ebdc1694853c5eb3bf37f82a5bd59b149d36cb3d408216315fecfbd2cce84f9e5186f24557c3c93827f6962b90fb1f49260250a55bb443c327e03e4a615be0bd5d290decc7fb6813d8b4d8c5619745cfaa1302987f2139ac71448aaf6");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    @Test
    public void testHmacDrbgNistWithPersonalizationCase7() {
        byte[] entropy = hex("b3679d82e8a26b602835fa5b780e9678d8a84cbcac2cd6a66174a9c6f68d86cb");
        byte[] nonce = hex("c4b1bcdcc20888953f8ff22363fa8a5e");
        byte[] personalizationString = hex("c35fe47a7a0c8f82af93ec7dd162aeb423af44aa71b35d299d561b65640e43a1");
        byte[] expected = hex("4fb91c1ac25546550d914c1bca10943f0bc2d6785d11eb28eef7b138b42a248b7866eeedee2a00772d3ea216430344af63e896fd0731e93ed7e06e96024385af563cc80cbb27df9285219b1abc57949c580b3d7b7b2e956ec2375ea5069c89ca50602494fbaf80b06424fda851a7576806dd18cb8c96e11c746b71e272c09eae0d3e1a5e14d46a4f09ce1520c36aee0e565f3093e703786bb109a4362620eec6108c0ae71abcb8e3a46e9907fa74522cf47f81d743ff4766b7ecf81982340e573a2893636ea8072373a628abe72e78fa22785e607d4fd6941b6b58da5d77517e3cd6d228b67b49b408b7b67c76ff126da818ba19e4dc36ab647f3b3bdb3c9e19");
        testDrbg(entropy, nonce, personalizationString, expected);
    }

    private void testDrbg(byte[] entropy, byte[] nonce, byte[] perso, byte[] expected) {
        testDrbg(MacFactory.Default.hmacSha512(), entropy, nonce, perso, expected, 2048);
    }
}