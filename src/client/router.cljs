(ns client.router
  (:require
   [client.events        :as events]
   [client.views         :refer [account demo landing lessonbuilder 
                                 lessons login signup]]
   [re-frame.core        :as rf]
   [reitit.coercion.spec :as rss]
   [reitit.frontend      :as rfe]
   [reitit.frontend.easy :as rfee]))

(def routes
  ["/"
   [""              {:name        ::landing
                     :view        #'landing
                     :link-text   "landing"
                     :controllers [{:start (fn [& params] (js/console.log "Entering landing page"))
                                    :stop  (fn [& params] (js/console.log "Leaving landing page"))}]}]

   ["account"       {:name      ::account
                     :view      #'account
                     :link-text "account"}]

   ["demo"          {:name      ::demo
                     :view      #'demo
                     :link-text "demo"}]

   ["lessonbuilder" {:name      ::lessonbuilder
                     :view      #'lessonbuilder
                     :link-text "lessonbuilder"}]

   ["lessons"       {:name      ::lessons
                     :view      #'lessons
                     :link-text "lessons"}]

   ["login"         {:name      ::login
                     :view      #'login
                     :link-text "login"}]

   ["signup"        {:name      ::signup
                     :view      #'signup
                     :link-text "signup"}]])
            
(defn on-navigate [new-match]
  (when new-match
    (rf/dispatch [::events/navigated new-match])))

(def router
  (rfe/router routes {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfee/start! router on-navigate {:use-fragment true}))

