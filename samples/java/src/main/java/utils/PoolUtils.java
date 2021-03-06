package utils;

import org.apache.commons.io.FileUtils;
import org.hyperledger.indy.sdk.IndyException;
import org.hyperledger.indy.sdk.pool.Pool;
import org.hyperledger.indy.sdk.pool.PoolJSONParameters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PoolUtils {

	private static final String DEFAULT_POOL_NAME = "default_pool";


	private static File createGenesisTxnFile(String filename) throws IOException {
		String path = EnvironmentUtils.getTmpPath(filename);
		String testPoolIp = EnvironmentUtils.getTestPoolIP();

		String[] defaultTxns = new String[]{
				String.format("{\"data\":{\"alias\":\"Node1\",\"client_ip\":\"%s\",\"client_port\":9702,\"node_ip\":\"%s\",\"node_port\":9701,\"services\":[\"VALIDATOR\"]},\"dest\":\"Gw6pDLhcBcoQesN72qfotTgFa7cbuqZpkX3Xo6pLhPhv\",\"identifier\":\"Th7MpTaRZVRYnPiabds81Y\",\"txnId\":\"fea82e10e894419fe2bea7d96296a6d46f50f93f9eeda954ec461b2ed2950b62\",\"type\":\"0\"}", testPoolIp, testPoolIp),
				String.format("{\"data\":{\"alias\":\"Node2\",\"client_ip\":\"%s\",\"client_port\":9704,\"node_ip\":\"%s\",\"node_port\":9703,\"services\":[\"VALIDATOR\"]},\"dest\":\"8ECVSk179mjsjKRLWiQtssMLgp6EPhWXtaYyStWPSGAb\",\"identifier\":\"EbP4aYNeTHL6q385GuVpRV\",\"txnId\":\"1ac8aece2a18ced660fef8694b61aac3af08ba875ce3026a160acbc3a3af35fc\",\"type\":\"0\"}", testPoolIp, testPoolIp),
				String.format("{\"data\":{\"alias\":\"Node3\",\"client_ip\":\"%s\",\"client_port\":9706,\"node_ip\":\"%s\",\"node_port\":9705,\"services\":[\"VALIDATOR\"]},\"dest\":\"DKVxG2fXXTU8yT5N7hGEbXB3dfdAnYv1JczDUHpmDxya\",\"identifier\":\"4cU41vWW82ArfxJxHkzXPG\",\"txnId\":\"7e9f355dffa78ed24668f0e0e369fd8c224076571c51e2ea8be5f26479edebe4\",\"type\":\"0\"}", testPoolIp, testPoolIp),
				String.format("{\"data\":{\"alias\":\"Node4\",\"client_ip\":\"%s\",\"client_port\":9708,\"node_ip\":\"%s\",\"node_port\":9707,\"services\":[\"VALIDATOR\"]},\"dest\":\"4PS3EDQ3dW1tci1Bp6543CfuuebjFrg36kLAUcskGfaA\",\"identifier\":\"TWwCRQRZ2ZHMJFn9TzLp7W\",\"txnId\":\"aa5e817d7cc626170eca175822029339a444eb0ee8f0bd20d3b0b76e566fb008\",\"type\":\"0\"}", testPoolIp, testPoolIp)
		};

		File file = new File(path);

		FileUtils.forceMkdirParent(file);

		FileWriter fw = new FileWriter(file);
		for (String defaultTxn : defaultTxns) {
			fw.write(defaultTxn);
			fw.write("\n");
		}

		fw.close();

		return file;
	}

	public static String createPoolLedgerConfig() throws IOException, InterruptedException, java.util.concurrent.ExecutionException, IndyException {
		File genesisTxnFile = createGenesisTxnFile("temp.txn");
		PoolJSONParameters.CreatePoolLedgerConfigJSONParameter createPoolLedgerConfigJSONParameter
				= new PoolJSONParameters.CreatePoolLedgerConfigJSONParameter(genesisTxnFile.getAbsolutePath());
		Pool.createPoolLedgerConfig(DEFAULT_POOL_NAME, createPoolLedgerConfigJSONParameter.toJson()).get();
		return DEFAULT_POOL_NAME;
	}
}
