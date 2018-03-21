/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package mvp.charco.com.mvplib.mvp;


/**
 * @author Corwin
 * 实现base接口的基类，提供了一个attachview()和detachview()的实现，
 * 用来处理mvpView的引用，可以从子类通过调用getMvpView()来获取MvpView的引用
 * @param <V>
 */
public interface Presenter<V extends MvpView> {

    /**
     * 关联
     * @param mvpView
     */
    void attachView(V mvpView);

    /**
     * 取消关联
     */
    void detachView();
}
