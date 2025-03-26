package re.notifica.push.cordova

import android.content.Context
import org.json.JSONObject
import re.notifica.models.NotificareNotification
import re.notifica.push.NotificarePushIntentReceiver
import re.notifica.push.models.NotificareNotificationDeliveryMechanism
import re.notifica.push.models.NotificareSystemNotification
import re.notifica.push.models.NotificareUnknownNotification

class NotificarePushPluginReceiver : NotificarePushIntentReceiver() {

        override fun onNotificationReceived(
            context: Context,
            notification: NotificareNotification,
            deliveryMechanism: NotificareNotificationDeliveryMechanism
        ) {
            try {
                val json = JSONObject()
                json.put("notification", notification.toJson())
                json.put("deliveryMechanism", deliveryMechanism.rawValue)

                NotificarePushPluginEventBroker.dispatchEvent("notification_info_received", json)
            } catch (e: Exception) {
                logger.error("Failed to emit the notification_info_received event.", e)
            }
        }

    override fun onSystemNotificationReceived(context: Context, notification: NotificareSystemNotification) {
        try {
            NotificarePushPluginEventBroker.dispatchEvent("system_notification_received", notification.toJson())
        } catch (e: Exception) {
            logger.error("Failed to emit the system_notification_received event.", e)
        }
    }

    override fun onUnknownNotificationReceived(context: Context, notification: NotificareUnknownNotification) {
        try {
            NotificarePushPluginEventBroker.dispatchEvent("unknown_notification_received", notification.toJson())
        } catch (e: Exception) {
            logger.error("Failed to emit the unknown_notification_received event.", e)
        }
    }

    override fun onNotificationOpened(context: Context, notification: NotificareNotification) {
        try {
            NotificarePushPluginEventBroker.dispatchEvent("notification_opened", notification.toJson())
        } catch (e: Exception) {
            logger.error("Failed to emit the notification_opened event.", e)
        }
    }

    override fun onActionOpened(
        context: Context,
        notification: NotificareNotification,
        action: NotificareNotification.Action
    ) {
        try {
            val json = JSONObject()
            json.put("notification", notification.toJson())
            json.put("action", action.toJson())

            NotificarePushPluginEventBroker.dispatchEvent("notification_action_opened", json)
        } catch (e: Exception) {
            logger.error("Failed to emit the notification_action_opened event.", e)
        }
    }
}
