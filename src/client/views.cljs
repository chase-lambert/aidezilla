(ns client.views 
  (:require
   [client.events        :as events]
   [client.router        :as-alias routes]
   [client.subs          :as subs]
   [re-frame.core        :as rf]))
   ;; [reitit.core          :as rc]
   ;; [reitit.frontend.easy :as rfee]))



(defn demo []
  [:div
   [:h1.text-3xl.font-extrabold 
    "DEMO"]
   [:button.btn.btn-primary.m-4
    {:on-click #(rf/dispatch [::events/push-state ::routes/landing])}
    "Homepage"]])

(defn signup []
  [:div 
   [:h1.text-3xl.font-extrabold 
    "Signup"]
   [:button.btn.btn-primary.m-4
    {:on-click #(rf/dispatch [::events/push-state ::routes/landing])}
    "Homepage"]])
   
(defn login []
  [:div 
   [:h1.text-3xl.font-extrabold 
    "Login"]
   [:button.btn.btn-primary.m-4
    {:on-click #(rf/dispatch [::events/push-state ::routes/landing])}
    "Homepage"]])

(defn landing []
  (let [name  @(rf/subscribe [::subs/name])
        count @(rf/subscribe [::subs/count])]
    [:div.m-4
     [:h1.text-3xl.font-extrabold.mb-4
      "Meet " name "!"]
     [:h2.font-extrabold
      "High-five counter: " count]
     [:button.btn.btn-primary.m-4
      {:on-click #(rf/dispatch [::events/increment-count])}
      "Up high!"]
     [:button.btn.btn-primary 
      {:on-click #(rf/dispatch [::events/decrement-count])}
      "Down low!"]
     [:br]
     [:button.btn.btn-primary.m-4
      {:on-click #(rf/dispatch [::events/push-state ::routes/demo])}
      "Go to demo"]]))

(defn lessonbuilder []
  [:div 
   [:h1.text-3xl.font-extrabold 
    "Lesson Builder"]
   [:button.btn.btn-primary.m-4
    {:on-click #(rf/dispatch [::events/push-state ::routes/landing])}
    "Homepage"]])

(defn lessons []
  [:div 
   [:h1.text-3xl.font-extrabold 
    "Lessons"]
   [:button.btn.btn-primary.m-4
    {:on-click #(rf/dispatch [::events/push-state ::routes/landing])}
    "Homepage"]])

(defn account []
  [:div 
   [:h1.text-3xl.font-extrabold 
    "Account"]
   [:button.btn.btn-primary.m-4
    {:on-click #(rf/dispatch [::events/push-state ::routes/landing])}
    "Homepage"]])

;; (defn href
;;   "return relative url for given route. Url can be used in HTML links."
;;   ([k] (href k nil nil))
;;   ([k params] (href k params nil))
;;   ([k params query] (rfee/href k params query)))

;; (defn nav [{:keys [router current-route]}]
;;   [:ul
;;    (for [route-name (rc/route-names router)
;;          :let [route (rc/match-by-name router route-name)
;;                text (-> route :data :link-text)]]
;;      [:li {:key route-name}
;;       (when (= route-name (-> current-route :data :name))
;;         "> ")
;;       [:a {:href (href route-name)} text]])])


(defn nav-item [{:keys [id name on-click active-page]}]
  [:li 
   [:a {:key      id
        :on-click on-click
        :class    (when (= id active-page) "underline decoration-2 underline-offset-8")}
    name]])

(def public-nav-items
  [{:id       :demo
    :name     "Demo"
    :on-click #(rf/dispatch [::events/push-state ::routes/demo])}
   {:id       :signup
    :name     "Sign Up"
    :on-click #(rf/dispatch [::events/push-state ::routes/signup])}
   {:id       :login
    :name     "Log In"
    :on-click #(rf/dispatch [::events/push-state ::routes/login])}])

(def auth-nav-items
  [{:id       :lessonbuilder
    :name     "Lesson Builder"
    :on-click #(rf/dispatch [::events/push-state ::routes/lessonbuilder])}
   {:id       :lessons
    :name     "Lessons"
    :on-click #(rf/dispatch [::events/push-state ::routes/lessons])}
   {:id       :profile
    :name     "Account"
    :on-click #(rf/dispatch [::events/push-state ::routes/account])}])

(defn navbar [nav-items]
  (let [current-route @(rf/subscribe [::subs/current-route])
        path (-> current-route :path)
        active-page (if (or (= path "/") (= path nil))
                      :landing
                      (keyword (subs path 1)))]
    [:div {:class "navbar bg-base-100"}
     [:div {:class "flex-1"}
      [:a {:class    "btn btn-ghost normal-case text-xl"
           :id       :landing
           :name     "Landing Page"
           :on-click #(rf/dispatch [::events/push-state ::routes/landing])}
       "Aidezilla"]]
     [:div {:class "flex-none"}
      [:ul {:class "menu menu-horizontal px-1"}
       (for [{:keys [id name on-click]} nav-items]
         ^{:key id}
         [nav-item {:id         id 
                    :name       name
                    :on-click   on-click
                    :active-page active-page}])]]]))

(defn nav []
  (if true
    (navbar public-nav-items)
    (navbar auth-nav-items)))

(defn router-component [{:keys [router]}]
  (let [current-route @(rf/subscribe [::subs/current-route])]
    [:div 
     ;; [nav {:router router :current-route current-route}]
     (when current-route 
       [(-> current-route :data :view)])])) 

(comment)
