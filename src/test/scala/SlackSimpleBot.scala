import akka.actor.ActorSystem
import slack.SlackUtil
import slack.rtm.SlackRtmClient

/**
 * Created by zkidkid on 12/26/16.
 */
object SlackSimpleBot extends App {
  val token = "xoxb-120068598164-6kwjQ8E1LUSabaWc7Pe8w70Y"
  implicit val system = ActorSystem("slack")
  implicit val ec = system.dispatcher

  val client = SlackRtmClient(token)
  val selfId = client.state.self.id
  val client2 = SlackRtmClient(token)
  client.onMessage { message =>

    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    if (mentionedIds.contains(selfId)) {
      client.sendMessage(message.channel, s"<@${message.user}>: Hey!")
    }
  }
  client2.onMessage { message =>
    println(message)
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)
    if (mentionedIds.contains(selfId))
      client.sendMessage(message.channel, s"Hey2!")

  }


}
