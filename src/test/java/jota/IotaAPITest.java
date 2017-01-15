package jota;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jota.dto.response.*;
import jota.error.ArgumentException;
import jota.error.InvalidBundleException;
import jota.error.InvalidSignatureException;
import jota.model.Bundle;
import jota.model.Transaction;
import jota.model.Transfer;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Let's do some integration test coverage against a default local real node.
 *
 * @author davassi
 */
public class IotaAPITest {

    private static Gson gson = new GsonBuilder().create();

    private static final String TEST_SEED1 = "AAA999999999999999999999999999999999999999999999999999999999999999999999999999999";
    private static final String TEST_SEED2 = "IHDEENZYITYVYSPKAURUZAQKGVJEREFDJMYTANNXXGPZ9GJWTEOJJ9IPMXOGZNQLSNMFDSQOTZAEETUEA";
    private static final String TEST_ADDRESS_WITHOUT_CHECKSUM = "PNGMCSNRCTRHCHPXYTPKEJYPCOWKOMRXZFHH9N9VDIKMNVAZCMIYRHVJIAZARZTUETJVFDMBEBIQE9QTH";
    private static final String TEST_ADDRESS_WITH_CHECKSUM = "PNGMCSNRCTRHCHPXYTPKEJYPCOWKOMRXZFHH9N9VDIKMNVAZCMIYRHVJIAZARZTUETJVFDMBEBIQE9QTHBFWDAOEFA";
    private static final String TEST_HASH = "CKZ9TYPLUWH9FUSYJMPIZBVHWFZXTZMVOJLC9KOICSTBBQWXYTOTMCVPSPMYNDONTXHRULRFAWD999999";
    private static final String TEST_TRYTES = "BYSWEAUTWXHXZ9YBZISEK9LUHWGMHXCGEVNZHRLUWQFCUSDXZHOFHWHL9MQPVJXXZLIXPXPXF9KYEREFSKCPKYIIKPZVLHUTDFQKKVVBBN9ATTLPCNPJDWDEVIYYLGPZGCWXOBDXMLJC9VO9QXTTBLAXTTBFUAROYEGQIVB9MJWJKXJMCUPTWAUGFZBTZCSJVRBGMYXTVBDDS9MYUJCPZ9YDWWQNIPUAIJXXSNLKUBSCOIJPCLEFPOXFJREXQCUVUMKSDOVQGGHRNILCO9GNCLWFM9APMNMWYASHXQAYBEXF9QRIHIBHYEJOYHRQJAOKAQ9AJJFQ9WEIWIJOTZATIBOXQLBMIJU9PCGBLVDDVFP9CFFSXTDUXMEGOOFXWRTLFGV9XXMYWEMGQEEEDBTIJ9OJOXFAPFQXCDAXOUDMLVYRMRLUDBETOLRJQAEDDLNVIRQJUBZBO9CCFDHIX9MSQCWYAXJVWHCUPTRSXJDESISQPRKZAFKFRULCGVRSBLVFOPEYLEE99JD9SEBALQINPDAZHFAB9RNBH9AZWIJOTLBZVIEJIAYGMC9AZGNFWGRSWAXTYSXVROVNKCOQQIWGPNQZKHUNODGYADPYLZZZUQRTJRTODOUKAOITNOMWNGHJBBA99QUMBHRENGBHTH9KHUAOXBVIVDVYYZMSEYSJWIOGGXZVRGN999EEGQMCOYVJQRIRROMPCQBLDYIGQO9AMORPYFSSUGACOJXGAQSPDY9YWRRPESNXXBDQ9OZOXVIOMLGTSWAMKMTDRSPGJKGBXQIVNRJRFRYEZ9VJDLHIKPSKMYC9YEGHFDS9SGVDHRIXBEMLFIINOHVPXIFAZCJKBHVMQZEVWCOSNWQRDYWVAIBLSCBGESJUIBWZECPUCAYAWMTQKRMCHONIPKJYYTEGZCJYCT9ABRWTJLRQXKMWY9GWZMHYZNWPXULNZAPVQLPMYQZCYNEPOCGOHBJUZLZDPIXVHLDMQYJUUBEDXXPXFLNRGIPWBRNQQZJSGSJTTYHIGGFAWJVXWL9THTPWOOHTNQWCNYOYZXALHAZXVMIZE9WMQUDCHDJMIBWKTYH9AC9AFOT9DPCADCV9ZWUTE9QNOMSZPTZDJLJZCJGHXUNBJFUBJWQUEZDMHXGBPTNSPZBR9TGSKVOHMOQSWPGFLSWNESFKSAZY9HHERAXALZCABFYPOVLAHMIHVDBGKUMDXC9WHHTIRYHZVWNXSVQUWCR9M9RAGMFEZZKZ9XEOQGOSLFQCHHOKLDSA9QCMDGCGMRYJZLBVIFOLBIJPROKMHOYTBTJIWUZWJMCTKCJKKTR9LCVYPVJI9AHGI9JOWMIWZAGMLDFJA9WU9QAMEFGABIBEZNNAL9OXSBFLOEHKDGHWFQSHMPLYFCNXAAZYJLMQDEYRGL9QKCEUEJ9LLVUOINVSZZQHCIKPAGMT9CAYIIMTTBCPKWTYHOJIIY9GYNPAJNUJ9BKYYXSV9JSPEXYMCFAIKTGNRSQGUNIYZCRT9FOWENSZQPD9ALUPYYAVICHVYELYFPUYDTWUSWNIYFXPX9MICCCOOZIWRNJIDALWGWRATGLJXNAYTNIZWQ9YTVDBOFZRKO9CFWRPAQQRXTPACOWCPRLYRYSJARRKSQPR9TCFXDVIXLP9XVL99ERRDSOHBFJDJQQGGGCZNDQ9NYCTQJWVZIAELCRBJJFDMCNZU9FIZRPGNURTXOCDSQGXTQHKHUECGWFUUYS9J9NYQ9U9P9UUP9YMZHWWWCIASCFLCMSKTELZWUGCDE9YOKVOVKTAYPHDF9ZCCQAYPJIJNGSHUIHHCOSSOOBUDOKE9CJZGYSSGNCQJVBEFTZFJ9SQUHOASKRRGBSHWKBCBWBTJHOGQ9WOMQFHWJVEG9NYX9KWBTCAIXNXHEBDIOFO9ALYMFGRICLCKKLG9FOBOX9PDWNQRGHBKHGKKRLWTBEQMCWQRLHAVYYZDIIPKVQTHYTWQMTOACXZOQCDTJTBAAUWXSGJF9PNQIJ9AJRUMUVCPWYVYVARKR9RKGOUHHNKNVGGPDDLGKPQNOYHNKAVVKCXWXOQPZNSLATUJT9AUWRMPPSWHSTTYDFAQDXOCYTZHOYYGAIM9CELMZ9AZPWB9MJXGHOKDNNSZVUDAGXTJJSSZCPZVPZBYNNTUQABSXQWZCHDQSLGK9UOHCFKBIBNETK999999999999999999999999999999999999999999999999999999999999999999999999999999999NOXDXXKUDWLOFJLIPQIBRBMGDYCPGDNLQOLQS99EQYKBIU9VHCJVIPFUYCQDNY9APGEVYLCENJIOBLWNB999999999XKBRHUD99C99999999NKZKEKWLDKMJCI9N9XQOLWEPAYWSH9999999999999999999999999KDDTGZLIPBNZKMLTOLOXQVNGLASESDQVPTXALEKRMIOHQLUHD9ELQDBQETS9QFGTYOYWLNTSKKMVJAUXSIROUICDOXKSYZTDPEDKOQENTJOWJONDEWROCEJIEWFWLUAACVSJFTMCHHXJBJRKAAPUDXXVXFWP9X9999IROUICDOXKSYZTDPEDKOQENTJOWJONDEWROCEJIEWFWLUAACVSJFTMCHHXJBJRKAAPUDXXVXFWP9X9999";
    private static final String TEST_MILESTONE = "SMYMAKKPSUKCKDRUEYCGZJTYCZ9HHDMDUWBAPXARGURPQRHTAJDASRWMIDTPTBNDKDEFBUTBGGAFX9999";
    private static final Integer TEST_MILESTONE_INDEX = 8059;
    private static final String TEST_MESSAGE = "JOTA";
    private static final String TEST_TAG = "JOTASPAM9999999999999999999";
    private static final String[] TEST_ADDRESSES = new String[]{"KHJXD9XKXPIVQRGREUIPVJTMEY9L9MXZAAKBBRYNINTIOXWBRMNLLW9MLGAXMGQWDBZLCOGFCBNKTDLDC"
            , "MQSAAEDPMKIAJPPGNLIHPQIVFGLHNEGG9JNMSHOQSVQHBQBMLNHY9WVRTCOYUOWOIJHBQXIQVFPDF9YRW"
            , "RGYOHMECRNVPYYPIAKEWHSOLBYOQPRFRPOJGHUMEGLICCUIPTZEXLWDLLPBNRXONUTQGLSAJSLHRXFVQD"
            , "FOJHXRVJRFMJTFDUWJYYZXCZIJXKQALLXMLKHZFDMHWTIBBXUKSNSUYJLKYRQBNXKRSUXZHDTPWXYD9YF"
            , "B9YNPQO9EXID9RDEEGLCBJBYKBLWHTOQOZKTLJDFPJZOPKJJTNUYUVVTDJPBCBYIWGPSCMNRZFGFHFSXH"
            , "NQEFOAFIYKZOUXDFQ9X9PHCNSDETRTJZINZ9EYGKU99QJLDSTSC9VTBAA9FHLNLNYQXWLTNPRJDWCGIPP"
            , "CEGLBSXDJVXGKGOUHRGMAQDRVYXCQLXBKUDWKFFSIABCUYRATFPTEEDIFYGAASKFZYREHLBIXBTKP9KLC"
            , "QLOXU9GIQXPPE9UUT9DSIDSIESRIXMTGZJMKLSJTNBCRELAVLWVJLUOLKGFCWAEPEQWZWPBV9YZJJEHUS"
            , "XIRMYJSGQXMM9YPHJVVLAVGBBLEEMOOKHHBFWKEAXJFONZLNSLBCGPQEVDMMOGHFVRDSYTETIFOIVNCR9"
            , "PDVVBYBXMHZKADPAYOKQNDPHRSWTHAWQ9GRVIBOIMZQTYCWEPCDWDVRSOUNASVBDLBOAMVLYEVVCMAM9N"
            , "U9GAIAPUUQWJGISAZWPLHUELTZ9WSHWXS9JLPKOWHRRIVUKGWCTJMBULVMKTETTUNHZ9HWHBALUCJIROU"
            , "VFPMKZLLMDUOEKNBEKQZPTNZJZF9UHRWSTHXLWQQ9OAXTZQHTZPAWNJNXKAZFSDFWKFQEKZIGJTLWQFLO"
            , "IGHK9XIWOAYBZUEZHQLEXBPTXSWVANIOUZZCPNKUIJIJOJNAQCJWUJHYKCZOIKVAAHDGAWJZKLTPVQL9G"
            , "LXQPWMNXSUZTEYNC9ZBBFHY9YWCCOVKBNIIOUSVXZJZMJKJFDUWGUVXYCHGKUHEEIDHSGEWFAHVJPRIJT"
            , "AKFDX9PGGQLZUWRMZ9YBDF9CG9TWXCNALCSXSAWHFIMGXCSYCJLSWIQDGGVDRMNEKKECQEYAITGNLNJFQ"
            , "YX9QSPYMSFVOW9UVZRDVOCPYYMUTDHCCPKHMXQSJQJYIXVCHILKW9GBYJTYGLIKBTRQMDCYBMLLNGSSIK"
            , "DSYCJKNG9TAGJHSKZQ9XLKAKNSKJFZIPVEDGJFXRTFGENHZFQGXHWDBNXLLDABDMOYELPG9DIXSNJFWAR"
            , "9ANNACZYLDDPZILLQBQG9YMG9XJUMTAENDFQ9HMSSEFWYOAXPJTUXBFTSAXDJPAO9FKTWBBSCSFMOUR9I"
            , "WDTFFXHBHMFQQVXQLBFJFVVHVIIAVYM9PFAZCHMKET9ESMHIRHSMVDJBZTXPTAFVIASMSXRDCIYVWVQNO"
            , "XCCPS9GMTSUB9DXPVKLTBDHOFX9PJMBYZQYQEXMRQDPGQPLWRGZGXODYJKGVFOHHYUJRCSXAIDGYSAWRB"
            , "KVEBCGMEOPDPRCQBPIEMZTTXYBURGZVNH9PLHKPMM9D9FUKWIGLKZROGNSYIFHULLWQWXCNAW9HKKVIDC"};

    private IotaAPI iotaClient;

    @Before
    public void createApiClientInstance() {
        iotaClient = new IotaAPI();
    }

    @Test
    public void shouldCreateIotaApiProxyInstanceWithDefaultValues() {
        IotaAPI proxy = new IotaAPI();
        assertThat(proxy, IsNull.notNullValue());
    }


    @Test
    public void shouldGetInputs() {
        GetBalancesAndFormatResponse res = iotaClient.getInputs(TEST_SEED1, null, 0, 0, 0);
        System.out.println(res);
        assertThat(res, IsNull.notNullValue());
        assertThat(res.getTotalBalance(), IsNull.notNullValue());
        assertThat(res.getInput(), IsNull.notNullValue());

    }


    @Test
    public void shouldCreateANewAddress() {
        final GetNewAddressResponse res = iotaClient.getNewAddress(TEST_SEED1, 0, false, 100, false);
        assertThat(res.getAddresses().get(0), Is.is(TEST_ADDRESS_WITHOUT_CHECKSUM));
        System.out.println(new Gson().toJson(res));
    }

    @Test
    public void shouldPrepareTransfer() {
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new jota.model.Transfer(TEST_ADDRESS_WITH_CHECKSUM, 0, TEST_MESSAGE, TEST_TAG));
        transfers.add(new jota.model.Transfer(TEST_ADDRESS_WITH_CHECKSUM, 1, TEST_MESSAGE, TEST_TAG));
        List<String> trytes = iotaClient.prepareTransfers(TEST_SEED1, transfers, null, null);
        Assert.assertNotNull(trytes);
        assertThat(trytes.isEmpty(), Is.is(false));
    }

    @Test
    public void shouldGetLastInclusionState() {
        GetInclusionStateResponse res = iotaClient.getLatestInclusion(new String[]{TEST_HASH});
        assertThat(res.getStates(), IsNull.notNullValue());
    }

    @Test
    public void shouldFindTransactionObjects() {
        List<Transaction> ftr = iotaClient.findTransactionObjects(TEST_ADDRESSES);
        assertThat(ftr, IsNull.notNullValue());
    }

    @Test
    public void shouldGetBundle() throws InvalidBundleException, ArgumentException, InvalidSignatureException {
        GetBundleResponse gbr = iotaClient.getBundle(TEST_HASH);
        assertThat(gbr, IsNull.notNullValue());
    }

    @Test
    public void shouldGetTransfers() throws InvalidBundleException, ArgumentException, InvalidSignatureException {
        GetTransferResponse gtr = iotaClient.getTransfers(TEST_SEED1, 0, 0, false);
        assertThat(gtr.getTransfers(), IsNull.notNullValue());

        for (Bundle test : gtr.getTransfers()) {
            for (Transaction trx : test.getTransactions()) {
                System.out.println(new Gson().toJson(trx));
            }
        }
    }
/*
    @Test
    public void shouldSendTrytes() {
        iotaClient.sendTrytes(new String[]{TEST_TRYTES}, 9, 18);
    }

    @Test
    public void shouldSendTransfer() throws InvalidBundleException, ArgumentException, InvalidSignatureException {
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new jota.model.Transfer(TEST_ADDRESS_WITHOUT_CHECKSUM, 0, "JUSTANOTHERTEST", TEST_TAG));
        SendTransferResponse str = iotaClient.sendTransfer(TEST_SEED2, 9, 18, transfers, null, null);
        assertThat(str.getSuccessfully(), IsNull.notNullValue());
    }
*/
}