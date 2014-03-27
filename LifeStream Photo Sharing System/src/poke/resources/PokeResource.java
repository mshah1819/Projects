/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.resources;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.DBInteraction;
import poke.server.Server;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;
import util.JPAManager;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;

import domain.Image;
import domain.User;

import eye.Comm.Header.ReplyStatus;
import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;

public class PokeResource implements Resource {
	protected static Logger logger = LoggerFactory.getLogger("server");

	public PokeResource() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.resources.Resource#process(eye.Comm.Finger)
	 */
	public GeneratedMessage process(Request req) {
		GeneratedMessage reply = null;
		// Always going to be a request input
		logger.info("Processing the REQUEST::" + req.getCorrelationId()
				+ " with Routing ID::" + req.getHeader().getRoutingId());
		boolean canProvideResponse = DBInteraction.getInstance()
				.canProcessRequest(req);
		logger.info("canProvideResponse::" + canProvideResponse);
		if (!canProvideResponse) {
			reply = req;
		} else {
			
			// Currently coded for retrieval of image..
			// Have to make it dynamic
				JPAManager mgr = new JPAManager(Server.configPath);
				System.out.println("Request to RETRIEVE IMAGE!!!");
				User user = new User();
				user.setUserId(req.getBody().getOperation().getRetrieveImage()
						.getUser().getUserId());
				System.out.println("RETRIEVING images for user::"
						+ user.getUserId());

				ArrayList<Image> arrImage = mgr.getImage(user);

				logger.info("No of images this user has:::"+arrImage.size());
				logger.info("Building a response!");
				Response.Builder r = Response.newBuilder();
				r.setHeader(ResourceUtil.buildHeaderFrom(req.getHeader(),
						ReplyStatus.SUCCESS, null));
				eye.Comm.PayloadReply.Builder br = PayloadReply.newBuilder();
				
				r.setBody(br.build());
				r.setCorrelationId(req.getCorrelationId());
				//r.setImage(ByteString.copyFrom(arrImage.get(0).getImage()));
				r.addImage(ByteString.copyFrom(arrImage.get(0).getImage()));
				reply = r.build();
			}
//			Response.Builder r = Response.newBuilder();
//            r.setHeader(ResourceUtil.buildHeaderFrom(req.getHeader(),
//                            ReplyStatus.SUCCESS, null));
//            eye.Comm.PayloadReply.Builder br = PayloadReply.newBuilder();
//            r.setBody(br.build());
//            r.setCorrelationId(req.getCorrelationId());
//            reply = r.build();
//            Response resp = (Response) reply;
//			logger.info("Sending RESPONSE::" + resp.getCorrelationId());
				
		return reply;
	}
}
