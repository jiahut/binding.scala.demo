package tutorial.webapp

import com.thoughtworks.binding.Binding.Vars
import org.scalajs.dom.raw.MouseEvent
import scala.scalajs.js.{ Function1, JSApp, JSON }
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html.Input
import org.scalajs.dom.raw.Event
import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.FutureBinding
import com.thoughtworks.binding.dom
import scala.util.{ Failure, Success }
import org.scalajs.dom.document
import scala.concurrent.ExecutionContext.Implicits._


object GithubAvatar extends JSApp {
  @dom
  def  tagPicker(tags: Vars[String], githubUserName: Var[String]) = {
    val input: Input = <input type="text" oninput={ {event: Event => githubUserName := event.currentTarget.asInstanceOf[Input].value} }/>
    val addHander = { event: Event =>
      if(input.value != "" && !tags.get.contains(input.value)) {
        tags.get += input.value
        input.value = ""
      }
    }
    <div>
    {
      for (tag <- tags) yield
      <span >
        <span onclick={ event: Event => githubUserName := tag }>{tag}</span>
        <button onclick={ event: Event => tags.get -= tag}> X </button>
      </span>
    }
    { input }
    <button onclick={ addHander} >Add</button>
    </div>
  }
  @dom
  def render = {
    val githubUserName = Var("")
    val tags = Vars("ruby", "python")
    <div>
        {tagPicker(tags, githubUserName).bind}
        <hr/>
        {
        val name = githubUserName.bind
        if(name == "") {
            <div> Please input your github user name </div>
        }else{
            val githubResult = FutureBinding(Ajax.get(s"https://api.github.com/users/${name}"))
            githubResult.bind match {
            case None =>
                <div>loading the avatar from {name } </div>
            case Some(Success(resp)) =>
                val json  = JSON.parse(resp.responseText)
                <img src={json.avatar_url.toString} />
            case Some(Failure(exception)) =>
                <div> { exception.toString} </div>
            }
        }
        }
    </div>
  }

  def main(): Unit = {
    println("Hello world!")
    dom.render(document.body, render)
    // dom.render(document.body)
  }
}
